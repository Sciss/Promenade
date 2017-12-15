/*
 *  Main.scala
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

import java.awt.Color
import javax.swing.SpinnerNumberModel

import de.sciss.kollflitz.Vec
import de.sciss.swingplus.{GroupPanel, Spinner}
import de.sciss.synth.impl.DefaultUGenGraphBuilderFactory
import de.sciss.synth.swing.ServerStatusPanel
import de.sciss.synth.{ControlSet, Server, ServerConnection, Synth, SynthDef, SynthGraph, UGenGraph}

import scala.swing.event.{ButtonClicked, ValueChanged}
import scala.swing.{Alignment, BorderPanel, Button, FlowPanel, Frame, Label, ScrollPane, Swing, ToggleButton}
import scala.util.Try

object Main {
  def findCtl(ug: UGenGraph, name: String): Vec[Float] = {
    val i       = ug.controlNames.indexWhere(_._1 == name)
    val off     = ug.controlNames(i)._2
    val stop    = if (i + 1 == ug.controlNames.size) ug.controlValues.size else ug.controlNames(i + 1)._2
    ug.controlValues.slice(off, stop)
  }

  def main(args: Array[String]): Unit = {
    val df          = SynthGraph(Input5.apply())
    val ug          = df.expand(DefaultUGenGraphBuilderFactory)
    val ctlValues0  = findCtl(ug, Promenade.paramCtlName)
    val numMix      = findCtl(ug, Promenade.mixCtlName  ).size
    Swing.onEDT(run(ug, ctlValues0, numMix = numMix))
  }

  def run(ug: UGenGraph, ctlValues0: Vec[Float], numMix: Int): Unit = {
    val paramValues = ctlValues0.toArray
    val mixValues   = Array.fill[Float](numMix)(1f)
    var syn         = Option.empty[Synth]

    def mkCtl(): Seq[ControlSet] = Seq(
      Promenade.paramCtlName -> paramValues.toVector,
      Promenade.mixCtlName   -> mixValues  .toVector
    )

    def setCtl(): Unit = {
      import de.sciss.synth.Ops._
      syn.foreach(_.set(mkCtl(): _*))
    }

    val ggParam = ctlValues0.zipWithIndex.map { case (v0, i) =>
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
//          spin.foreground = if (vNow == v0) Color.black else Color.blue
//          spin.peer.getEditor.setBackground(if (vNow == v0) Color.white else Color.yellow)
          ggReset.background = if (vNow == v0) null else Color.yellow
          setCtl()
      }
      Seq(lb, spin, ggReset, ggZero)
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
      val _syn = Synth.play("neg", args = mkCtl())
      val _old = syn
      Try(_old.foreach(_.free()))
      syn = Some(_syn)
    }

    val ggPlay = Button("Play") {
      mkPlay()
    }

    val bp = new BorderPanel {
      add(new FlowPanel(sp, ggPlay) , BorderPanel.Position.North  )
      add(new ScrollPane(g)         , BorderPanel.Position.Center )
      if (numMix > 1) add(new FlowPanel(ggMix: _*), BorderPanel.Position.South  )
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
