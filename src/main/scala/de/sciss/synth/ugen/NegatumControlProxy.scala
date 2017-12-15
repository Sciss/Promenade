package de.sciss.synth.ugen

import de.sciss.synth.UGenSource.Vec
import de.sciss.synth.{ControlRated, UGen, UGenGraph, control}

import scala.collection.breakOut

object NegatumControlProxy {
  private val factory: ControlFactoryLike = new ControlFactoryLike {
    override def build(b: UGenGraph.Builder, proxies: Vec[ControlProxyLike]): Map[ControlProxyLike, (UGen, Int)] = {
      val values = proxies.flatMap(_.values)
      val numChannels = values.size
      val specialIndex = b.addControl(values, Some(ctlName))
      val ugen = makeUGen(numChannels, specialIndex)
      var offset = 0
      proxies.map { p =>
        val res = p -> ((ugen, offset))
        offset += p.values.size
        res
      } (breakOut)
    }

    protected def makeUGen(numChannels: Int, specialIndex: Int): UGen =
      impl.ControlImpl("Control", control, numChannels = numChannels, specialIndex = specialIndex)
  }

  final val ctlName = "$neg"
}
final case class NegatumControlProxy(value: Double)
  extends ControlProxyLike with ControlRated {

  def values: Vec[Float] = Vector.empty :+ value.toFloat

  def name: Option[String] = Some(NegatumControlProxy.ctlName)

  private[synth] def factory: ControlFactoryLike = NegatumControlProxy.factory
}