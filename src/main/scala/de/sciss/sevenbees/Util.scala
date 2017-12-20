/*
 *  Util.scala
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

import de.sciss.synth.{GE, Ops}
import de.sciss.synth.ugen.{Mix, NegatumControlProxy}

object Util {
  final val paramCtlName  = "par"
  final val mixCtlName    = "mix"

  final val inf = Float.PositiveInfinity

  implicit class ConstOps(private val d: Double) extends AnyVal {
    def ! : GE = {
      NegatumControlProxy(d)
    }
  }

  def mkMix(seq: GE*): GE = {
    import Ops.stringToControl
    val amp = Util.mixCtlName.kr(Vector.fill(seq.size)(1f))
    val sig = (seq: GE) * amp
    Mix(sig)
  }
}