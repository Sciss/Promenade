/*
 *  Input4.scala
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

object Input4 {
  import Promenade._

  def apply(): Unit = { // dac2395e
    val p1              = -365.4939.!
    val p2              = -1764.7299.!
    val p3              = 11909.468.!
    val in_0            = Protect(0.7853982, -inf, inf, true)
    val onePole_0       = OnePole.ar(in_0, coeff = 0.7853982)
    val in_1            = Protect(0.7853982, -inf, inf, true)
    val release_0       = Protect(458.78067, 0.0, 30.0, false)
    val in_2            = Decay2.ar(in_1, attack = 0.7853982, release = release_0)
    val freq_0          = Protect(in_2, -inf, inf, false)
    val lFDNoise1       = LFDNoise1.ar(freq_0)
    val in_3            = Protect(p1, -inf, inf, true)
    val b_0             = LPF.ar(in_3, freq = 109.13051)
    val in_4            = Protect(onePole_0, -inf, inf, true)
    val freq_1          = Protect(0.6362062, 10.0, 20000.0, false)
    val rq_0            = Protect(lFDNoise1, 0.01, 100.0, false)
    val bRF             = BRF.ar(in_4, freq = freq_1, rq = rq_0)
    val freq_2          = in_2 >= lFDNoise1
    val in_5            = Protect(0.057681356, -inf, inf, true)
    val coeff_0         = Protect(4336.367, -0.999, 0.999, false)
    val integrator      = Integrator.ar(in_5, coeff = coeff_0)
    val in_6            = Protect(0.017736461, -inf, inf, true)
    val freq_3          = Protect(integrator, 10.0, 20000.0, false)
    val rq_1            = Protect(-3.083139, 0.01, 100.0, false)
    val loop            = RLPF.ar(in_6, freq = freq_3, rq = rq_1)
    val a_0             = 12.9085655 + loop
    val k               = HenonL.ar(freq = freq_2, a = a_0, b = b_0, x0 = -1.460452, x1 = 2.0636463)
    val in_7            = Protect(b_0, -inf, inf, true)
    val time_0          = Protect(270.48257, 0.0, 30.0, false)
    val lag3            = Lag3.ar(in_7, time = time_0)
    val min_0           = k min lag3
    val min_1           = onePole_0 min p3
    val min_2           = min_1 min 0.7853982
    val min_3           = min_2 min 60.217957
    val min_4           = min_3 min 60.217957
    val min_5           = 60.217957 min min_4
    val min_6           = min_5 min 60.217957
    val min_7           = min_6 min 60.217957
    val min_8           = min_7 min 60.217957
    val min_9           = min_8 min 60.217957
    val min_10          = min_9 min 60.217957
    val min_11          = min_10 min 60.217957
    val min_12          = 0.017736461 min bRF
    val decayTime       = min_12 min bRF
    val freq_4          = Protect(decayTime, 10.0, 20000.0, false)
    val width_0         = Protect(31.334932, 0.0, 1.0, false)
    val b_1             = Pulse.ar(freq = freq_4, width = width_0)
    val yi_0            = Protect(b_1, -inf, inf, false)
    val standardN       = StandardN.ar(freq = -179.19797, k = 1.0, xi = -117.8114, yi = yi_0)
    val min_13          = p3 min standardN
    val minus           = min_13 - standardN
    val min_14          = minus min standardN
    val min_15          = min_14 min standardN
    val min_16          = min_15 min standardN
    val in_8            = Protect(3729.9978, -inf, inf, true)
    val dur_0           = Protect(in_2, 0.0, 30.0, false)
    val ramp            = Ramp.ar(in_8, dur = dur_0)
    val b_2             = ramp min k
    val in_9            = Protect(decayTime, -inf, inf, true)
    val time_1          = Protect(bRF, 0.0, 30.0, false)
    val lag             = Lag.ar(in_9, time = time_1)
    val min_17          = lag min bRF
    val xi_0            = Protect(min_17, -inf, inf, false)
    val quadL_0         = QuadL.ar(freq = 0.017736461, a = 0.026830098, b = b_2, c = -179.19797, xi = xi_0)
    val iphase_1        = Protect(quadL_0, 0.0, 1.0, false)
    val lFPar_1         = LFPar.ar(freq = 440.0, iphase = iphase_1)
    val min_18          = lFPar_1 min k
    val min_19          = min_18 min min_16
    val a_1             = min_19 min min_11
    val min_20          = a_1 min p2
    val min_21          = lag3 min min_0
    val b_3             = min_21 min lag3
    val min_22          = b_3 min lag3
    val min_23          = min_22 min k
    val in_10           = Protect(min_0, -inf, inf, true)
    val time_2          = Protect(min_23, 0.0, 30.0, false)
    val a_2             = Lag2.ar(in_10, time = time_2)
    val in_11           = Protect(-1.460452, -inf, inf, true)
    val attack_0        = Protect(a_2, 0.0, 30.0, false)
    val release_1       = Protect(min_20, 0.0, 30.0, false)
    val decay2_0        = Decay2.ar(in_11, attack = attack_0, release = release_1)
    val min_24          = min_2 min lag3
    val d               = min_24 min lag3
    val min_25          = d min min_2
    val min_26          = min_25 min min_2
    val min_27          = min_26 min min_2
    val min_28          = min_27 min min_2
    val min_29          = min_28 min min_2
    val min_30          = min_29 min min_2
    val min_31          = min_30 min min_2
    val min_32          = min_31 min min_2
    val in_13           = Protect(min_12, -inf, inf, true)
    val maxDelayTime_0  = Protect(min_2, 0.0, 20.0, false)
    val protect_0       = Protect(min_32, 0.0, inf, false)
    val delayTime_0     = protect_0 min maxDelayTime_0
    val allpassN        = AllpassN.ar(in_13, maxDelayTime = maxDelayTime_0, delayTime = delayTime_0,
      decayTime = 0.7853982)
    val ring3           = 0.017736461 ring3 onePole_0
    val in_14           = Protect(ring3, -inf, inf, true)
    val hPZ2            = HPZ2.ar(in_14)
    val in_15           = Protect(ramp, -inf, inf, true)
    val time_3          = Protect(allpassN, 0.0, 30.0, false)
    val lag2_0          = Lag2.ar(in_15, time = time_3)
    val freq_5          = Protect(lag2_0, 0.01, 20000.0, false)
    val iphase_2        = Protect(hPZ2, 0.0, 1.0, false)
    val width_1         = Protect(-0.22109115, 0.0, 1.0, false)
    val lFPulse         = LFPulse.ar(freq = freq_5, iphase = iphase_2, width = width_1)
    val min_33          = min_11 min 60.217957
    val min_34          = min_33 min 60.217957
    val min_35          = min_34 min 60.217957
    val min_36          = min_35 min 60.217957
    val min_37          = min_36 min 60.217957
    val min_38          = min_37 min 60.217957
    val in_16           = Protect(0.057681356, -inf, inf, true)
    val maxDelayTime_1  = Protect(60.217957, 0.0, 20.0, false)
    val protect_1       = Protect(min_38, 0.0, inf, false)
    val delayTime_1     = protect_1 min maxDelayTime_1
    val combN           = CombN.ar(in_16, maxDelayTime = maxDelayTime_1, delayTime = delayTime_1,
      decayTime = 1.0)
    val freq_6          = Protect(in_2, -inf, inf, false)
    val xi_1            = Protect(min_13, -inf, inf, false)
    val quadC           = QuadC.ar(freq = freq_6, a = -0.6915745, b = combN, c = 109.13051, xi = xi_1)
    val min_39          = 3729.9978 min lag3
    val min_40          = min_39 min lag3
    val min_41          = min_40 min lag3
    val min_42          = min_41 min lag3
    val min_43          = min_42 min lag3
    val min_44          = min_43 min lag3
    val in_17           = Protect(lFDNoise1, -inf, inf, true)
    val maxDelayTime_2  = Protect(lag3, 0.0, 20.0, false)
    val protect_2       = Protect(min_44, 0.0, inf, false)
    val delayTime_2     = protect_2 min maxDelayTime_2
    val allpassC        = AllpassC.ar(in_17, maxDelayTime = maxDelayTime_2, delayTime = delayTime_2,
      decayTime = decayTime)
    val min_45          = min_1 min k
    val min_46          = min_45 min lag3
    val roundTo         = min_46 roundTo lag3
    val min_47          = roundTo min lag3
    val min_48          = min_47 min lag3
    val in_18           = Protect(quadC, -inf, inf, true)
    val bPZ2            = BPZ2.ar(in_18)
    val mod             = bRF % bPZ2
    val fold2_0         = mod fold2 allpassC
    val min_49          = fold2_0 min 60.217957
    val min_50          = min_49 min min_48
    val min_51          = min_50 min 60.217957
    val min_52          = min_51 min 60.217957
    val min_53          = min_52 min 60.217957
    val min_54          = min_53 min 60.217957
    val min_55          = min_20 min min_54
    val min_56          = min_55 min min_15
    val min_57          = min_56 min min_24
    val min_85          = p1 min k
    val in_25           = Protect(270.48257, -inf, inf, true)
    val onePole_1       = OnePole.ar(in_25, coeff = 0.057681356.!)
    val xi_3            = Protect(allpassN, -inf, inf, false)
    val gbmanL_0        = GbmanL.ar(freq = p3, xi = xi_3, yi = 3729.9978)
    val in_26           = Protect(-0.6915745, -inf, inf, true)
    val coeff_2         = Protect(60.217957, -1.0, 1.0, false)
    val oneZero_1       = OneZero.ar(in_26, coeff = coeff_2)
    val min_88          = oneZero_1 min 60.217957
    val min_89          = min_88 min 60.217957
    val a_3             = min_89 min 60.217957
    val in_30           = Protect(270.48257, -inf, inf, true)
    val freq_11         = Protect(p1, 10.0, 20000.0, false)
    val lPF             = LPF.ar(in_30, freq = freq_11)
    val min_96          = lPF min 458.78067
    val a_4             = Protect(min_96, -3.0, 3.0, false)
    val b_6             = Protect(0.19079351, 0.5, 1.5, false)
    val xi_4            = Protect(b_0, -inf, inf, false)
    val latoocarfianC   = LatoocarfianC.ar(freq = 0.017736461, a = a_4, b = b_6, c = 0.89118487,
      d = 2.0636463, xi = xi_4, yi = 0.06592734)
    val yi_2            = Protect(min_36, -inf, inf, false)
    val m               = StandardL.ar(freq = lag3, k = k, xi = 0.0011183836, yi = yi_2)
    val in_31           = Protect(latoocarfianC, -inf, inf, true)
    val freq_12         = Protect(m, 10.0, 20000.0, false)
    val rq_2            = Protect(onePole_1, 0.01, 100.0, false)
    val b_7             = RHPF.ar(in_31, freq = freq_12, rq = rq_2)
    val freq_13         = Protect(decay2_0, -inf, inf, false)
    val henonL_1        = HenonL.ar(freq = freq_13, a = a_3, b = b_7, x0 = 2674.3022, x1 = 3729.9978)
    val min_97          = min_56 min min_96
    val min_98          = min_97 min 3.2220234E-4
    val min_99          = min_98 min lFPulse
    val min_100         = min_99 min ring3
    val min_101         = min_100 min 6.0210614
    val ring2_1         = onePole_1 ring2 min_57
    val mix             = mkMix(
        min_85,
        gbmanL_0,
        henonL_1,
        min_101,
        ring2_1,
    )
    NegatumOut(mix)
  }
}
