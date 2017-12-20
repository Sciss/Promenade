/*
 *  Explore.scala
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

import java.awt.Color
import javax.swing.SpinnerNumberModel

import de.sciss.kollflitz.Vec
import de.sciss.{numbers, osc}
import de.sciss.swingplus.{GroupPanel, Spinner}
import de.sciss.synth.impl.DefaultUGenGraphBuilderFactory
import de.sciss.synth.swing.ServerStatusPanel
import de.sciss.synth.{Bus, ControlSet, Server, ServerConnection, Synth, SynthDef, SynthGraph, UGenGraph, addToHead, addToTail}

import scala.swing.event.{ButtonClicked, ValueChanged}
import scala.swing.{Alignment, BorderPanel, BoxPanel, Button, FlowPanel, Frame, Label, Orientation, ScrollPane, Slider, Swing, ToggleButton}
import scala.util.Try

object Explore {
  def main(args: Array[String]): Unit = {
    Swing.onEDT(run(Bee7))
  }

  def findCtl(ug: UGenGraph, name: String): Vec[Float] = {
    val i       = ug.controlNames.indexWhere(_._1 == name)
    val off     = ug.controlNames(i)._2
    val stop    = if (i + 1 == ug.controlNames.size) ug.controlValues.size else ug.controlNames(i + 1)._2
    ug.controlValues.slice(off, stop)
  }

  def run(input: Bee): Unit = {
    val df          = SynthGraph(input())
    val ug          = df.expand(DefaultUGenGraphBuilderFactory)
    val ctlValues0  = findCtl(ug, Util.paramCtlName)
    val numMix      = findCtl(ug, Util.mixCtlName  ).size

    val paramValues = ctlValues0.toArray
    val mixValues   = Array.fill[Float](numMix)(1f)
    var syn         = Option.empty[Synth]

    def mkCtl(): Seq[ControlSet] = Seq(
      Util.paramCtlName -> paramValues.toVector,
      Util.mixCtlName   -> mixValues  .toVector
    )

    def setCtl(): Unit = {
      import de.sciss.synth.Ops._
      syn.foreach(_.set(mkCtl(): _*))
    }

    val (mParam, ggParam) = ctlValues0.zipWithIndex.map { case (v0, i) =>
      val m     = new SpinnerNumberModel(v0, Double.MinValue, Double.MaxValue, 0.01)
      val lb    = new Label(i.toString, Swing.EmptyIcon, Alignment.Trailing)
      val spin  = new Spinner(m)
      val d     = spin.preferredSize
      d.width   = math.min(400, d.width)
      spin.preferredSize  = d
      spin.minimumSize    = d
      spin.maximumSize    = d
      spin.listenTo(spin)
      val ggReset = Button("Reset") {
        m.setValue(v0.asInstanceOf[AnyRef])
      }
      val ggZero = Button("Zero") {
        m.setValue(0.0)
      }
      spin.reactions += {
        case ValueChanged(_) =>
          val vNow = m.getNumber.floatValue()
          paramValues(i) = vNow
          ggReset.background = if (vNow == v0) null else Color.yellow
          setCtl()
      }
      m -> Seq(lb, spin, ggReset, ggZero)
    } .unzip

    def updateParamGadgets(): Unit = {
      (mParam zip paramValues).foreach { case (gg, v) => gg.setValue(v.asInstanceOf[AnyRef]) }
    }

    val g = new GroupPanel {
      type E = GroupPanel.Element
      horizontal = Seq(
        ggParam.transpose.map { col => Par(col.map(x => x: E): _*) }: _*
      )
      vertical = Seq(
        ggParam.map { row => Par(Baseline)(row.map(x => x: E): _*) }: _*
      )
    }

    val ggMix = Seq.tabulate(numMix) { i =>
      val gg = new ToggleButton(f"$i%2s")
      gg.selected = true
      gg.listenTo(gg)
      gg.reactions += {
        case ButtonClicked(_) =>
          mixValues(i) = if (gg.selected) 1f else 0f
          setCtl()
      }
      gg
    }

    val sp = new ServerStatusPanel

    def mkPlay(): Unit = {
      import de.sciss.synth.Ops._
      val _old = syn
      syn = None
      Try(_old.foreach(_.free()))
      val _syn = sp.server.map { s =>
        Synth.play("neg", target = s, addAction = addToTail, args = mkCtl())
      }
      syn = _syn
    }

    val ggPlay = Button("Play") {
      mkPlay()
    }

    def printParams(in: Vec[Float]): Unit = {
      val s = in.map(f => f"$f%ff").mkString("Vector(", ", ", ")")
      println(s)
    }

    def store(idx: Int): Unit = {
      input.situations(idx) = paramValues.toVector
      printParams(input.situations(idx))
    }

    val ggStore1 = Button("Store 1")(store(0))
    val ggStore2 = Button("Store 2")(store(1))

    def recall(idx: Int): Unit = {
      System.arraycopy(input.situations(idx).toArray, 0, paramValues, 0, paramValues.length)
      updateParamGadgets()
      setCtl()
    }

    val ggRecall1 = Button("Recall 1")(recall(0))
    val ggRecall2 = Button("Recall 2")(recall(1))

    val ggInterp  = new Slider {
      min   =   0
      max   = 100
      value =   0
      listenTo(this)
      reactions += {
        case ValueChanged(_) =>
          import numbers.Implicits._
          val w = value.linlin(min, max, 0.0f, 1f)
          (input.situations(0) zip input.situations(1)).zipWithIndex.foreach { case ((a, b), i) =>
            paramValues(i) = w.linlin(0f, 1f, a, b)
          }
          updateParamGadgets()
          setCtl()
      }
    }

    var synLine = Option.empty[Synth]

    def toggleLine(onOff: Boolean): Unit = {
      import de.sciss.synth.Ops._
      val _old = synLine
      synLine = None
      Try(_old.foreach(_.free()))
      if (onOff) {
        val _syn = sp.server.map { s =>
          val numParams = paramValues.length
          val df = SynthDef("line") {
            import de.sciss.synth._
            import ugen._
            val from    = "a"   .ir(Vector.fill(numParams)(0f))
            val to      = "b"   .ir(Vector.fill(numParams)(0f))
            val dur     = "dur" .ir
            val bus     = "bus" .kr
            val curve   = Curve.sine
//            val ln    = Line.ar(from, to, dur)
//            val ln    = EnvGen.ar(Env(from, List(Env.Segment(dur, to, curve))))
            val index0  = Line.ar(0, 1, dur)
            val index   = index0.roundTo(0.01) * dur
            val ln      = IEnvGen.ar(IEnv(from, List(Env.Segment(dur, to, curve))), index = index)
//            ln.poll(1, "ln")
            Out.ar(bus, ln)
          }

          val bus = Bus.audio(s, numParams)

          val res = Synth(s)

          df.recv(s, completion = { df: SynthDef =>
            val mNew = res.newMsg(df.name, target = s, addAction = addToHead, args = List(
              "a" -> input.situations(0), "b" -> input.situations(1), "dur" -> 30f, "bus" -> bus.index))
            val mMap = syn.get.mapanMsg(Util.paramCtlName -> bus)
            s ! osc.Bundle.now(mNew, mMap)
            res.onEnd {
              bus.free()
            }
          })
          res
    //
    //        val res = df.play(target = s, args = List(
    //          "from" -> input.situations(0), "to" -> input.situations(1), "dur" -> 30f, "bus" -> bus.index))
    //        res.onEnd {
    //          bus.free()
    //        }
    //        res
        }
        synLine = _syn
      }
    }

    val ggLine = new ToggleButton("Line") {
      listenTo(this)
      reactions += {
        case ButtonClicked(_) => toggleLine(onOff = selected)
      }
    }

    val pTop    = new FlowPanel(sp, ggPlay)
    val pStore  = new FlowPanel(ggStore1, ggStore2, ggRecall1, ggRecall2, ggInterp, ggLine)
    val pBot    = if (numMix <= 1) pStore else {
      val pMix = new FlowPanel(ggMix: _*)
      new BoxPanel(Orientation.Vertical) {
        contents += pStore
        contents += pMix
      }
    }

    val bp = new BorderPanel {
      add(pTop              , BorderPanel.Position.North  )
      add(new ScrollPane(g) , BorderPanel.Position.Center )
      add(pBot              , BorderPanel.Position.South)
    }

    new Frame {
      contents = bp
      pack().centerOnScreen()
      open()

      override def closeOperation(): Unit = {
        sp.server.foreach(_.quit())
        sys.exit(0)
      }
    }

    def mkBoot(): Unit =
      sp.booting = Some(Server.boot() {
        case ServerConnection.Running(s) =>
          import de.sciss.synth.Ops._
//          s.dumpOSC()
          SynthDef("neg", ug).recv(s, completion = { _: SynthDef =>
            Swing.onEDT {
              sp.server = Some(s)
              mkPlay()
            }
          })
      })

    sp.bootAction = Some(() => mkBoot())
    mkBoot()
  }
}
