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
import de.sciss.synth.ugen.NegatumControlProxy
import de.sciss.synth.{ControlSet, Server, ServerConnection, Synth, SynthDef, SynthGraph, UGenGraph}

import scala.swing.event.ValueChanged
import scala.swing.{Alignment, BorderPanel, Button, FlowPanel, Frame, Label, ScrollPane, Swing}

object Main {
  def main(args: Array[String]): Unit = {
    val df = SynthGraph(Inputs.neg_9905128())
//    println(s"Num constants: ${Inputs.count}; unique ${Inputs.set.size}")
//    println(df.controlProxies.size)
    val ug = df.expand(DefaultUGenGraphBuilderFactory)
//    println("control names")
//    println(ug.controlNames)
//    println("control value")
//    println(ug.controlValues)
    val i       = ug.controlNames.indexWhere(_._1 == NegatumControlProxy.ctlName)
    val off     = ug.controlNames(i)._2
    val stop    = if (i + 1 == ug.controlNames.size) ug.controlValues.size else ug.controlNames(i + 1)._2
    val numCtl  = stop - off

//    assert(ug.controlNames.headOption.contains(NegatumControlProxy.ctlName -> 0), ug.controlNames)
//    assert(ug.controlNames.size > 1)
//    val numCtl      = ug.controlNames(1)._2
    val ctlValues0  = ug.controlValues.slice(off, stop)

    Swing.onEDT(run(ug, ctlValues0))
  }

  def run(ug: UGenGraph, ctlValues0: Vec[Float]): Unit = {
    val ctlValues = ctlValues0.toArray
    var syn       = Option.empty[Synth]

    def mkCtl: ControlSet =
      NegatumControlProxy.ctlName -> ctlValues.toVector

    val gg = ctlValues0.zipWithIndex.map { case (v0, i) =>
      val m     = new SpinnerNumberModel(v0, Double.MinValue, Double.MaxValue, 0.01)
      val lb    = new Label(i.toString, Swing.EmptyIcon, Alignment.Trailing)
      val spin  = new Spinner(m)
      val d     = spin.preferredSize
      d.width   = math.min(400, d.width)
      spin.preferredSize  = d
      spin.minimumSize    = d
      spin.maximumSize    = d
      spin.listenTo(spin)
      val b   = Button("Reset") {
        m.setValue(v0.asInstanceOf[AnyRef])
      }
      spin.reactions += {
        case ValueChanged(_) =>
          val vNow = m.getNumber.floatValue()
          ctlValues(i) = vNow
//          spin.foreground = if (vNow == v0) Color.black else Color.blue
//          spin.peer.getEditor.setBackground(if (vNow == v0) Color.white else Color.yellow)
          b.background = if (vNow == v0) null else Color.yellow
          import de.sciss.synth.Ops._
          syn.foreach(_.set(mkCtl))
      }
      Seq(lb, spin, b)
    }

    val g = new GroupPanel {
      type E = GroupPanel.Element
      horizontal = Seq(
        gg.transpose.map { col => Par(col.map(x => x: E): _*) }: _*
      )
      vertical = Seq(
        gg.map { row => Par(Baseline)(row.map(x => x: E): _*) }: _*
      )
    }

    val sp = new ServerStatusPanel

    def mkPlay(): Unit = {
      import de.sciss.synth.Ops._
      val _syn = Synth.play("neg", args = mkCtl :: Nil)
      val _old = syn
      _old.foreach(_.free())
      syn = Some(_syn)
    }

    val ggPlay = Button("Play") {
      mkPlay()
    }

    val bp = new BorderPanel {
      add(new FlowPanel(sp, ggPlay), BorderPanel.Position.North)
      add(new ScrollPane(g), BorderPanel.Position.Center)
    }

    new Frame {
      contents = bp
      pack().centerOnScreen()
      open()

      override def closeOperation(): Unit = {
        sp.server.foreach(_.dispose())
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
