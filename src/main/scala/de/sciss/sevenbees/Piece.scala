/*
 *  Piece.scala
 *  (Seven Bees)
 *
 *  Copyright (c) 2017 Hanns Holger Rutz. All rights reserved.
 *
 *  This software is published under the GNU General Public License v2+
 *
 *
 *  For further information, please contact Hanns Holger Rutz at
 *  contact@sciss.de
 */

package de.sciss.sevenbees

import de.sciss.file._
import de.sciss.numbers.Implicits._
import de.sciss.osc
import de.sciss.sevenbees.Explore.findCtl
import de.sciss.synth.impl.DefaultUGenGraphBuilderFactory
import de.sciss.synth.io.AudioFileType
import de.sciss.synth.ugen.Env.{Segment => Seg}
import de.sciss.synth.ugen.NegatumOut
import de.sciss.synth.{Buffer, Bus, Completion, Server, Synth, SynthDef, SynthGraph, addAfter, addToHead, addToTail}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}

object Piece {
  final case class Config(usePan: Boolean = true, ampScale: Double  = -21.dbamp, recordFile: Option[File] = None)

  def complete[A](fun: => (Completion[A]) => Unit): Unit = {
    val p = Promise[Unit]()
    fun(Completion.fromFunction((_: A) => p.success(())))
    Await.result(p.future, Duration.Inf)
  }

  val NUM_BREAKS  : Int     = 50 // 100
  val FADE_IN     : Double  = 60.0 * 2
  val FADE_OUT    : Double  = 60.0 * 2
  val FADE_CROSS  : Double  = 60.0 * 3 // 6
  val DLY_BETWEEN : Double  = 30.0
  val DUR_SCALE   : Double  = 1.0

  val DEBUG_POLL  : Boolean = false

  def main(args: Array[String]): Unit = {
    val default = Config()

    val p = new scopt.OptionParser[Config]("Seven Bees") {
      opt[Boolean] ("stereo")
        .text (s"Use stereo setup with channel duplication (default: ${default.usePan})")
        .action { (v, c) => c.copy(usePan = v) }

      opt[Double] ("amp")
        .text (f"Scale the amplitude, in decibels (default: ${default.ampScale.ampdb}%1.1f)")
        .action { (v, c) => c.copy(ampScale = v.dbamp) }

      opt[File]("record")
        .text ("File to record output to; format will be AIFF float (default: none)")
        .action { (v, c) => c.copy(recordFile = Some(v)) }
    }
    p.parse(args, default).fold(sys.exit(1))(run)
  }

  def run(config: Config): Unit = {
    import config._
    NegatumOut.PAN2 = usePan
    NegatumOut.AMP  = true

    val numInputs       = Bee.seq.size
    val numOutChans     = if (usePan) numInputs * 2 else numInputs
    val c               = Server.Config()
    c.inputBusChannels  = 0
    c.outputBusChannels = numOutChans
    c.sampleRate        = 44100
    c.deviceName        = Some("Promenade")

    Server.run(c) { s =>
      // s.dumpOSC()
      booted(s, numInputs = numInputs, numOutChans = numOutChans, config = config)
    }
  }

  final case class Channel()

  def booted(s: Server, numInputs: Int, numOutChans: Int, config: Config): Unit = {
    import de.sciss.synth.Ops._
    import config._

    val durFadeIn   = FADE_IN     * DUR_SCALE
    val durFadeOut  = FADE_OUT    * DUR_SCALE
    val durCross    = FADE_CROSS  * DUR_SCALE
    val durDly1     = DLY_BETWEEN * DUR_SCALE
    val durDlyAll   = durDly1 * (numInputs - 1)

    val messages0 = Bee.seq.zipWithIndex.flatMap { case (input, idx) =>
      val sg          = SynthGraph { input() }
      val ug          = sg.expand(DefaultUGenGraphBuilderFactory)
      val ctlValues0  = findCtl(ug, Util.paramCtlName)
//      val numMix      = findCtl(ug, Seven Bees.mixCtlName  ).size
      val numParams   = ctlValues0.size
      val dfMain      = SynthDef(s"neg$idx", ug)
      complete[SynthDef](dfMain.recv(s, _))

      val dfMap      = SynthDef(s"fd$idx") {
        import de.sciss.synth._
        import ugen._
        val from    = input.situations(0): GE // "a"   .ir(Vector.fill(numParams)(0f))
        val to      = input.situations(1): GE // "b"   .ir(Vector.fill(numParams)(0f))
//        val dur     = "dur" .ir
        val bus     = "bus" .kr
        val curve   = Curve.sine
        //            val ln    = Line.ar(from, to, dur)
        //            val ln    = EnvGen.ar(Env(from, List(Env.Segment(dur, to, curve))))

        val index0  = EnvGen.ar(Env(0.0, List(Seg(durFadeIn + durDlyAll, 0), Seg(durCross, 1))))
        if (DEBUG_POLL && idx == 0) index0.poll(1, "indx-0")
        if (DEBUG_POLL && idx == 1) index0.poll(1, "indx-1")
//        val index0  = Line.ar(0, 1, dur)
        val dustPeriod = durCross / NUM_BREAKS
//        val index1  = index0.roundTo(1.0/NUM_BREAKS)
        val index1  = Latch.ar(index0, Dust.ar(dustPeriod.reciprocal))
        val index   = index1 * durCross
        val ln      = IEnvGen.ar(IEnv(from, List(Seg(durCross, to, curve))), index = index)
        //            ln.poll(1, "ln")
        Out.ar(bus, ln)
      }

      complete[SynthDef](dfMap.recv(s, _))

      val synMain     = Synth(s)
      val synMap      = Synth(s)
      val out         = if (usePan) idx * 2 else idx
      val busMap      = Bus.audio(s, numChannels = numParams)
      val mNewMain    = synMain.newMsg(dfMain.name, target = s, addAction = addToTail,
        args = List("out" -> out, "amp" -> input.amp * ampScale))
      val mNewMap     = synMap.newMsg(dfMap.name, target = s, addAction = addToHead,
        args = List("bus" -> busMap.index))
      val mMap        = synMain.mapanMsg(Util.paramCtlName -> busMap)
      // val mRun        = syn.runMsg(false)
      List(mNewMain, mNewMap, mMap) // , mRun)
    }

    val dfMaster = SynthDef("master") {
      import de.sciss.synth._
      import ugen._
      val in                    = In.ar(0, numOutChans)
      val floor                 = -48.dbamp
      val ceil                  = 1.0 //  + floor
      val durFdInDly      : GE  = Vector.tabulate(numInputs)(idx => idx * durDly1)
      val durCrossBrutto  : GE  = Vector.tabulate(numInputs)(idx => (numInputs - 1 - idx) * durDly1 + durCross)
      val totalDur = durDlyAll + 1.0 + durFadeIn + durCross + durFadeOut
      println(f"total-dur $totalDur%1.1f sec.")
      Line.kr(totalDur, 0, totalDur).roundTo(1).poll(Impulse.kr(1), "t")
      val env   = Env(floor, List(Seg(durFdInDly, floor), Seg(durFadeIn, ceil, Curve.exp), Seg(durCrossBrutto, ceil), Seg(durFadeOut, floor, Curve.exp)))
      val fd    = EnvGen.ar(env, doneAction = freeAllInGroup) - floor
      if (DEBUG_POLL) {
        (fd \ 0).poll(1, "main-0")
        (fd \ 1).poll(1, "main-1")
      }
      val sig   = in * fd
      ReplaceOut.ar(0, sig)
    }

    complete[SynthDef](dfMaster.recv(s, _))
    val synMaster = Synth(s)
    val mNewMaster = synMaster.newMsg(dfMaster.name, target = s, addAction = addToTail,
      args = Nil)

    val messages1 = messages0 :+ mNewMaster

    val messages = recordFile.fold(messages1) { fOut =>
      val bufRec = Buffer(s)

      import scala.concurrent.ExecutionContext.Implicits.global

      val bufFut = for {
        _   <- bufRec.alloc(numFrames = 32768, numChannels = numOutChans)
        fut <- bufRec.write(path = fOut.path, fileType = AudioFileType.AIFF, numFrames = 0, leaveOpen = true)
      } yield fut

      Await.result(bufFut, Duration.Inf)

      val dfRec = SynthDef("rec") {
        import de.sciss.synth._
        import ugen._
        val in = In.ar(0, numOutChans)
        DiskOut.ar(bufRec.id, in)
      }

      complete[SynthDef](dfRec.recv(s, _))
      val synRec = Synth(s)
      val mNewRec = synRec.newMsg(dfRec.name, target = synMaster, addAction = addAfter,
        args = Nil)

      synRec.onEnd {
        println("Recording finished.")
        bufRec.close(bufRec.freeMsg)
        Thread.sleep(4000)
        s.quit()
        sys.exit()
      }

      messages1 :+ mNewRec
    }

    s ! osc.Bundle.now(messages: _*)
  }
}
