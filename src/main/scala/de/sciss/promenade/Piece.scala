/*
 *  Piece.scala
 *  (Promenade)
 *
 *  Copyright (c) 2017 Hanns Holger Rutz. All rights reserved.
 *
 *  This software is published under the GNU General Public License v2+
 *
 *
 *  For further information, please contact Hanns Holger Rutz at
 *  contact@sciss.de
 */

package de.sciss.promenade

import de.sciss.osc
import de.sciss.promenade.Explore.findCtl
import de.sciss.numbers.Implicits._
import de.sciss.synth.impl.DefaultUGenGraphBuilderFactory
import de.sciss.synth.ugen.{Env, NegatumOut}
import de.sciss.synth.{Bus, Completion, Server, Synth, SynthDef, SynthGraph, addAfter, addToHead, addToTail}
import Env.{Segment => Seg}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}

object Piece {
  def complete[A](fun: => (Completion[A]) => Unit): Unit = {
    val p = Promise[Unit]()
    fun(Completion.fromFunction((_: A) => p.success(())))
    Await.result(p.future, Duration.Inf)
  }

  val USE_PAN     : Boolean = true
  val NUM_BREAKS  : Int     = 50 // 100
  val FADE_IN     : Double  = 60.0 * 2
  val FADE_OUT    : Double  = 60.0 * 2
  val FADE_CROSS  : Double  = 60.0 * 3 // 6
  val DLY_BETWEEN : Double  = 30.0
  val DUR_SCALE   : Double  = 1.0
  val AMP_SCALE   : Double  = -21.dbamp

  val DEBUG_POLL  : Boolean = false

  def main(args: Array[String]): Unit = {
    NegatumOut.PAN2 = USE_PAN
    NegatumOut.AMP  = true

    val numInputs       = Input.seq.size
    val numOutChans     = if (USE_PAN) numInputs * 2 else numInputs
    val c               = Server.Config()
    c.inputBusChannels  = 0
    c.outputBusChannels = numOutChans
    c.deviceName        = Some("Promenade")

    Server.run(c) { s =>
      //      s.dumpOSC()
      run(s, numInputs = numInputs, numOutChans = numOutChans)
    }
  }

  final case class Channel()

  def run(s: Server, numInputs: Int, numOutChans: Int): Unit = {
    import de.sciss.synth.Ops._

    val durFadeIn   = FADE_IN     * DUR_SCALE
    val durFadeOut  = FADE_OUT    * DUR_SCALE
    val durCross    = FADE_CROSS  * DUR_SCALE
    val durDly1     = DLY_BETWEEN * DUR_SCALE
    val durDlyAll   = durDly1 * (numInputs - 1)

    val messages0 = Input.seq.zipWithIndex.flatMap { case (input, idx) =>
      val sg          = SynthGraph { input() }
      val ug          = sg.expand(DefaultUGenGraphBuilderFactory)
      val ctlValues0  = findCtl(ug, Promenade.paramCtlName)
//      val numMix      = findCtl(ug, Promenade.mixCtlName  ).size
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
      val out         = if (USE_PAN) idx * 2 else idx
      val busMap      = Bus.audio(s, numChannels = numParams)
      val mNewMain    = synMain.newMsg(dfMain.name, target = s, addAction = addToTail,
        args = List("out" -> out, "amp" -> input.amp * AMP_SCALE))
      val mNewMap     = synMap.newMsg(dfMap.name, target = s, addAction = addToHead,
        args = List("bus" -> busMap.index))
      val mMap        = synMain.mapanMsg(Promenade.paramCtlName -> busMap)
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
      val totalDur = durDlyAll + durDlyAll + durFadeIn + durCross + durFadeOut
      Line.kr(totalDur, 0, totalDur).roundTo(1).poll(1, "t")
      val env   = Env(floor, List(Seg(durFdInDly, floor), Seg(durFadeIn, ceil, Curve.exp), Seg(durCrossBrutto, ceil), Seg(durFadeOut, floor, Curve.exp)))
      val fd    = EnvGen.ar(env) - floor
      if (DEBUG_POLL) {
        (fd \ 0).poll(1, "main-0")
        (fd \ 1).poll(1, "main-1")
      }
      val sig   = in * fd
      ReplaceOut.ar(0, sig)
    }

    complete[SynthDef](dfMaster.recv(s, _))
    val synMaster = Synth(s)
    val mNewMaster = synMaster.newMsg(dfMaster.name, target = s, addAction = addAfter,
      args = Nil)

    val messages = messages0 :+ mNewMaster

    s ! osc.Bundle.now(messages: _*)
  }
}
