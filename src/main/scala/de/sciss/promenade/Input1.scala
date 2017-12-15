/*
 *  Input1.scala
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

import de.sciss.synth._
import de.sciss.synth.ugen._

object Input1 {
  import Promenade._

  def apply(): Unit = { // 9905128
    // NegatumIn()
    val p1                = 5814.3823.!
    val p2                = 2298.9329.!
    val in_0              = Protect(-607.21533, -inf, inf, true)
    val maxDelayTime_0    = Protect(-607.21533, 0.0, 20.0, false)
    val protect_0         = Protect(-607.21533, 0.0, inf, false)
    val delayTime_0       = protect_0 min maxDelayTime_0
    val delayL            = DelayL.ar(in_0, maxDelayTime = maxDelayTime_0, delayTime = delayTime_0)
    val in_1              = Protect(-607.21533, -inf, inf, true)
    val time_0            = Protect(delayL, 0.0, 30.0, false)
    val lag3              = Lag3.ar(in_1, time = time_0)
    val freq_0            = Protect(delayL, 0.01, 20000.0, false)
    val iphase_0          = Protect(lag3, -1.0, 1.0, false)
    val a_0               = LFSaw.ar(freq = freq_0, iphase = iphase_0)
    val freq_1            = Protect(a_0, -inf, inf, false)
    val xi_0              = Protect(a_0, -inf, inf, false)
    val a_1               = LinCongC.ar(freq = freq_1, a = 1.1, c = -607.21533, m = delayL, xi = xi_0)
    val freq_2            = Protect(a_1, -inf, inf, false)
    val henonC_0          = HenonC.ar(freq = freq_2, a = a_1, b = 0.3, x0 = 0.0072869626, x1 = 0.0072869626)
    val freq_3            = Protect(henonC_0, 10.0, 20000.0, false)
    val numHarm           = Protect(a_1, 1.0, inf, false)
    val phase_0           = Blip.ar(freq = freq_3, numHarm = numHarm)
    val sinOsc            = SinOsc.ar(freq = p1, phase = phase_0)
    val ring4             = p2 ring4 sinOsc
    val mix               = mkMix(
      ring4,
    )
    NegatumOut(mix)
  }
}
