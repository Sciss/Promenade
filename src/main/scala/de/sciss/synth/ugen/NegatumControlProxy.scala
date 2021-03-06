/*
 *  NegatumControlProxy.scala
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

package de.sciss.synth.ugen

import de.sciss.sevenbees.Util
import de.sciss.synth.UGenSource.Vec
import de.sciss.synth.{AudioRated, UGen, UGenGraph, audio}

import scala.collection.breakOut

object NegatumControlProxy {
  private val factory: ControlFactoryLike = new ControlFactoryLike {
    override def build(b: UGenGraph.Builder, proxies: Vec[ControlProxyLike]): Map[ControlProxyLike, (UGen, Int)] = {
      val values = proxies.flatMap(_.values)
      val numChannels = values.size
      val specialIndex = b.addControl(values, Some(Util.paramCtlName))
      val ugen = makeUGen(numChannels, specialIndex)
      var offset = 0
      proxies.map { p =>
        val res = p -> ((ugen, offset))
        offset += p.values.size
        res
      } (breakOut)
    }

    protected def makeUGen(numChannels: Int, specialIndex: Int): UGen =
      impl.ControlImpl("AudioControl", audio, numChannels = numChannels, specialIndex = specialIndex)
  }
}
final case class NegatumControlProxy(value: Double)
  extends ControlProxyLike with AudioRated {

  def values: Vec[Float] = Vector.empty :+ value.toFloat

  def name: Option[String] = Some(Util.paramCtlName)

  private[synth] def factory: ControlFactoryLike = NegatumControlProxy.factory
}