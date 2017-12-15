package de.sciss.promenade

import de.sciss.synth.UGenSource.Vec

object Input {
  val seq: Seq[Input] = Seq(
    Input1, Input2, Input3, Input4, Input5, Input6, Input7
  )
}
trait Input {
  def apply(): Unit

  val situations: Array[Vec[Float]] = Array.fill(2)(Vector.empty)
}