/*
 *  Input2.scala
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

object Input2 extends Input {
  import Promenade._

  def apply(): Unit = { // b65a1e0d
    // NegatumIn()
    val c0              = 0.030771492 // .!
    val p1              = -4524.843.!
    val p2              = 1300.5251.!
    val p3              = 1.1.!
    val in_0            = Protect(c0, -inf, inf, true)
    val delay1          = Delay1.ar(in_0)
    val linCongL        = LinCongL.ar(freq = Nyquist() /* could not parse! */, a = p3, c = c0,
      m = c0, xi = c0)
    val in_1            = Protect(linCongL, -inf, inf, true)
    val combC           = CombC.ar(in_1, maxDelayTime = 0.2, delayTime = 0.2, decayTime = c0)
    val in_2            = Protect(linCongL, -inf, inf, true)
    val d_0             = HPZ2.ar(in_2)
    val min_0           = delay1 min d_0
    val d_1             = HenonL.ar(freq = c0, a = min_0, b = 2186.5337, x0 = 2186.5337,
      x1 = 2186.5337)
    val freq_0          = Protect(p1, 0.01, 20000.0, false)
    val iphase_0        = Protect(linCongL, -1.0, 1.0, false)
    val c_0             = LFSaw.ar(freq = freq_0, iphase = iphase_0)
    val coeff_0         = Protect(5.664139E-4, 0.8, 0.99, false)
    val leakDC          = LeakDC.ar(combC, coeff = coeff_0)
    val in_4            = Protect(371.5311, -inf, inf, true)
    val a_0             = LPZ1.ar(in_4)
    val freq_1          = Protect(leakDC, -inf, inf, false)
    val b_0             = Protect(2186.5337, 0.5, 1.5, false)
    val c_1             = Protect(371.5311, 0.5, 1.5, false)
    val xi_0            = Protect(a_0, -inf, inf, false)
    val m_0             = LatoocarfianL.ar(freq = freq_1, a = 1.0, b = b_0, c = c_1, d = d_1, xi = xi_0,
      yi = 30.413992)
    val fold2           = linCongL fold2 m_0
    val freq_2          = Protect(fold2, -inf, inf, false)
    val linCongC_0      = LinCongC.ar(freq = freq_2, a = a_0, c = c_0, m = m_0, xi = p2)
    val hypot           = linCongC_0 hypot 79.40235
    val roundUpTo       = d_0 roundUpTo p1
    val mix = mkMix(
      hypot,
      roundUpTo,
    )
    NegatumOut(mix)
  }
}
