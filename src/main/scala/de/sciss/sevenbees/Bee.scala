package de.sciss.sevenbees

import de.sciss.synth.UGenSource.Vec

object Bee {
  val seq: Seq[Bee] = Seq(
    Bee1, Bee2, Bee3, Bee4, Bee5, Bee6, Bee7
  )
}
trait Bee {
  def apply(): Unit

  val situations: Array[Vec[Float]] = Array.fill(2)(Vector.empty)

  var amp: Double = 1.0
}