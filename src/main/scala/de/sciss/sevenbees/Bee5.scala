/*
 *  Bee5.scala
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

import de.sciss.synth._
import de.sciss.synth.ugen._

object Bee5 extends Bee {
  import Util._

  situations(0) = Vector(3.995302f, 3210.302979f, 0.500000f, -1810.795776f, 0.200000f, 85.629959f, 0.039563f, 0.024074f)
//  situations(1) = Vector(-995.000000f, 1.303000f, 1.360000f, -910.796021f, 0.400000f, 295.630005f, 0.010000f, -1.024000f)
//  situations(1) = Vector(-995.000000f, 3210.302979f, 3.000000f, 0.000000f, 0.200000f, 85.629959f, 0.039563f, 0.024074f)
  situations(1) = Vector(22115.000000f, 3210.302979f, 2.720000f, 0.000000f, 0.200000f, 85.629959f, 0.039563f, 0.024074f)

  // e8944a47
  def apply(): Unit = {
    // NegatumIn()
    val p1              = 3.9953024.!
    val p2              = 3210.303.!
    val p3              = 0.5.!
    val p4              = -1810.7958.!
    val p5              = 0.2.!
    val p6              = 85.62996.!
    val p7              = 0.03956314.!
    val p8              = 0.024074255.!
    val lFDNoise3_0     = LFDNoise3.ar(p4)
    val in_0            = Protect(lFDNoise3_0, -inf, inf, true)
    val hPZ2_0          = HPZ2.ar(in_0)
    val in_1            = Protect(1238.7404, -inf, inf, true)
    val time_0          = Protect(p2, 0.0, 30.0, false)
    val lag             = Lag.ar(in_1, time = time_0)
    val leq             = lag <= p7
    val wrap2_0         = hPZ2_0 wrap2 leq
    val difsqr          = wrap2_0 difsqr 0.097936
    val in_2            = Protect(404.16705, -inf, inf, true)
    val coeff_0         = Protect(3463.753, -1.0, 1.0, false)
    val decayTime       = OneZero.ar(in_2, coeff = coeff_0)
    val freq_0          = Protect(decayTime, 0.1, 20000.0, false)
    val phase_0         = Protect(3463.753, 0.0, 1.0, false)
    val impulse         = Impulse.ar(freq = freq_0, phase = phase_0)
    val gbmanN          = GbmanN.ar(freq = Nyquist() /* could not parse! */, xi = 0.02031959,
      yi = 8.525862)
    val in_4            = Protect(410.9399, -inf, inf, true)
    val a_0             = LPZ2.ar(in_4)
    val min_0           = a_0 min p5
    val min_1           = min_0 min p5
    val min_2           = min_1 min p5
    val min_3           = min_2 min p5
    val min_4           = min_3 min p5
    val min_5           = p5 min min_4
    val c_0             = min_5 min p5
    val linCongC        = LinCongC.ar(freq = 2887.6274, a = -0.03924294, c = c_0, m = 16.338713,
      xi = 2296.401)
    val min_9           = linCongC min p1
    val min_10          = p1 min min_9
    val min_11          = min_10 min p1
    val min_12          = min_11 min p1
    val min_13          = min_12 min p1
    val min_14          = p1 min min_13
    val min_15          = min_14 min p1
    val min_16          = p1 min min_15
    val min_17          = min_16 min p1
    val min_18          = min_17 min p1
    val a_1             = Protect(404.16705, -3.0, 3.0, false)
    val b_0             = Protect(404.16705, 0.5, 1.5, false)
    val xi_0            = Protect(impulse, -inf, inf, false)
    val a_2             = LatoocarfianL.ar(freq = Nyquist() /* could not parse! */, a = a_1, b = b_0,
      c = p3, d = p8, xi = xi_0, yi = 0.02031959)
    val pow             = p7 pow a_2
    val freq_1          = Protect(pow, -inf, inf, false)
    val cuspL           = CuspL.ar(freq = freq_1, a = a_0, b = -0.018404199, xi = 2296.401)
    val min_19          = p5 min cuspL
    val min_20          = min_19 min cuspL
    val in_6            = Protect(0.028970052, -inf, inf, true)
    val lPF             = LPF.ar(in_6, freq = p2)
    val min_21          = cuspL min lPF
    val min_22          = lPF min min_21
    val min_23          = min_22 min cuspL
    val min_24          = min_23 min cuspL
    val min_25          = min_24 min cuspL
    val in_7            = Protect(min_25, -inf, inf, true)
    val maxDelayTime_0  = Protect(cuspL, 0.0, 20.0, false)
    val protect_0       = Protect(min_20, 0.0, inf, false)
    val delayTime_0     = protect_0 min maxDelayTime_0
    val delayC          = DelayC.ar(in_7, maxDelayTime = maxDelayTime_0, delayTime = delayTime_0)
    val min_26          = cuspL min delayC
    val in_8            = Protect(lag, -inf, inf, true)
    val allpassC        = AllpassC.ar(in_8, maxDelayTime = 3.9953024, delayTime = p1,
      decayTime = decayTime)
    val min_27          = min_18 min p1
    val min_28          = min_27 min p1
    val min_29          = min_28 min p1
    val freq_2          = Protect(min_29, -inf, inf, false)
    val lFDNoise0       = LFDNoise0.ar(freq_2)
    val min_30          = min_29 min p1
    val min_31          = min_30 min p1
    val min_32          = min_31 min p1
    val min_37          = min_32 min p6
    val min_38          = min_37 min min_32
    val min_39          = min_38 min min_32
    val min_40          = min_39 min min_32
    val min_42          = min_40 min cuspL
    val min_43          = p2 min allpassC
    val min_44          = min_43 min allpassC
    val min_45          = min_26 min min_44
    val a_3             = min_45 min min_44
    val min_46          = a_3 min min_44
    val min_47          = min_46 min min_44
    val min_48          = min_47 min min_44
    val min_49          = min_48 min min_42
    val min_50          = min_49 min min_44
    val min_51          = min_50 min min_44
    val min_52          = min_51 min min_44
    val min_53          = min_52 min min_44
    val min_54          = min_53 min min_44
    val min_55          = min_54 min min_44
    val min_56          = min_55 min min_44
    val min_57          = min_56 min min_44
    val min_65          = allpassC min 3487.2532
    val in_10           = Protect(gbmanN, -inf, inf, true)
    val freq_3          = Protect(min_65, 10.0, 20000.0, false)
    val rq              = Protect(lFDNoise0, 0.01, 100.0, false)
    val rHPF            = RHPF.ar(in_10, freq = freq_3, rq = rq)
    val b_1             = min_42 min cuspL
    val sawFreq         = Protect(0.0014495701, 0.01, 20000.0, false)
    val syncSaw         = SyncSaw.ar(syncFreq = 3463.753, sawFreq = sawFreq)
    val min_71          = impulse min min_18
    val freq_4          = Protect(min_71, -inf, inf, false)
    val xi_1            = Protect(syncSaw, -inf, inf, false)
    val cuspN           = CuspN.ar(freq = freq_4, a = a_3, b = b_1, xi = xi_1)
    val min_72          = cuspN min a_3
    val min_73          = min_72 min a_3
    val min_74          = min_73 min a_3
    val min_75          = min_74 min a_3
    val in_11           = Protect(pow, -inf, inf, true)
    val maxDelayTime_2  = Protect(a_3, 0.0, 20.0, false)
    val protect_2       = Protect(min_75, 0.0, inf, false)
    val delayTime_2     = protect_2 min maxDelayTime_2
    val b_2             = DelayN.ar(in_11, maxDelayTime = maxDelayTime_2, delayTime = delayTime_2)
    val min_76          = min_51 min p6
    val freq_5          = Protect(min_76, -inf, inf, false)
    val xi_2            = Protect(a_2, -inf, inf, false)
    val quadN           = QuadN.ar(freq = freq_5, a = a_2, b = b_2, c = 2887.6274, xi = xi_2)
    val freq_6          = Protect(quadN, -inf, inf, false)
    val lFDClipNoise    = LFDClipNoise.ar(freq_6)
    val wrap2_1         = 8.525862 wrap2 lFDClipNoise
    val roundUpTo       = min_42 roundUpTo p4
    val freq_8          = Protect(roundUpTo, -inf, inf, false)
    val lFDNoise3_1     = LFDNoise3.ar(freq_8)
    val clip2           = lFDNoise3_1 clip2 c_0
    val in_13           = Protect(min_57, -inf, inf, true)
    val time_1          = Protect(clip2, 0.0, 30.0, false)
    val lag2            = Lag2.ar(in_13, time = time_1)
    val a_4             = Protect(lag2, -3.0, 3.0, false)
    val b_3             = Protect(lFDNoise3_1, 0.5, 1.5, false)
    val c_1             = Protect(wrap2_1, 0.5, 1.5, false)
    val yi_0            = Protect(difsqr, -inf, inf, false)
    val latoocarfianN   = LatoocarfianN.ar(freq = 3463.753, a = a_4, b = b_3, c = c_1, d = p3,
      xi = 3210.303, yi = yi_0)
    val freq_9          = Protect(cuspL, 0.01, 20000.0, false)
    val lFCub           = LFCub.ar(freq = freq_9, iphase = 0.0)
    val in_14           = Protect(lFDNoise3_1, -inf, inf, true)
    val hPZ2_1          = HPZ2.ar(in_14)
    val min_81          = hPZ2_0 min hPZ2_1
    val min_82          = min_81 min min_22
    val min_83          = min_82 min p1
    val min_84          = min_83 min lFCub
    val in_15           = Protect(pow, -inf, inf, true)
    val dur             = Protect(min_82, 0.0, 30.0, false)
    val ramp            = Ramp.ar(in_15, dur = dur)
    val mix = mkMix(
      rHPF,
      latoocarfianN,
      min_84,
      ramp
    )
    NegatumOut(mix)
  }
}