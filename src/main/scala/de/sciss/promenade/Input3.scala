/*
 *  Input3.scala
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

object Input3 {
  import Promenade._

  def apply(): Unit = { // f7dcc808
    // NegatumIn()
    val p1              = 5.377026.!
    val p2              = 5470.0537.!
    val p3              = -681.2247.!
    val p4              = -77.65894.!
    val p5              = 2.5775092.!
    val p6              = 0.037651896.!
    val p7              = 0.9676637.!
    val syncSaw_0       = SyncSaw.ar(syncFreq = 0.023090454, sawFreq = p1)
    val freq_0          = Protect(syncSaw_0, -inf, inf, false)
    val linCongN        = LinCongN.ar(freq = freq_0, a = 0.6100561, c = p4, m = p1,
      xi = 0.023090454)
    val coeff_0         = Protect(p2, 0.8, 0.99, false)
    val leakDC_0        = LeakDC.ar(p5, coeff = coeff_0)
    val in_0            = Protect(p5, -inf, inf, true)
    val lag             = Lag.ar(in_0, time = p5)
    val in_1            = Protect(p4, -inf, inf, true)
    val freq_1          = Protect(p6, 10.0, 20000.0, false)
    val radius_0        = Protect(-0.0012890837, 0.0, 1.0, false)
    val twoPole_0       = TwoPole.ar(in_1, freq = freq_1, radius = radius_0)
    val thresh          = lag thresh twoPole_0
    val amclip          = thresh amclip 0.6751442
    val freq_2          = Protect(leakDC_0, 10.0, 20000.0, false)
    val pulse           = Pulse.ar(freq = freq_2, width = 0.007397973)
    val bitAnd          = pulse & p2
    val in_2            = Protect(linCongN, -inf, inf, true)
    val freq_3          = Protect(amclip, 10.0, 20000.0, false)
    val rq_0            = Protect(bitAnd, 0.01, 100.0, false)
    val bPF             = BPF.ar(in_2, freq = freq_3, rq = rq_0)
    val min_0           = -0.0016006872 min bPF
    val min_1           = min_0 min bPF
    val min_2           = min_1 min bPF
    val min_3           = min_2 min bPF
    val min_4           = min_3 min bPF
    val runningSum_1    = RunningSum.ar(73.90736, length = 440.0)
    val in_3            = Protect(p1, -inf, inf, true)
    val lPZ2_0          = LPZ2.ar(in_3)
    val in_4            = Protect(5464.677, -inf, inf, true)
    val decay           = Decay.ar(in_4, time = 1.0)
    val atan2           = p6 atan2 leakDC_0
    val min_11          = p5 min bPF
    val min_12          = min_11 min bPF
    val min_13          = min_12 min bPF
    val min_14          = min_13 min bPF
    val b_0             = 0.6100561.! fold2 atan2
    val in_6            = Protect(-0.0016006872, -inf, inf, true)
    val attack_0        = Protect(-1465.4102, 0.0, 30.0, false)
    val decay2_0        = Decay2.ar(in_6, attack = attack_0, release = 0.0015277232)
    val min_15          = min_14 min decay2_0
    val min_16          = min_15 min decay2_0
    val min_17          = min_16 min decay2_0
    val min_18          = min_17 min decay2_0
    val min_19          = min_18 min decay2_0
    val min_20          = min_19 min decay2_0
    val min_21          = min_20 min decay2_0
    val min_22          = min_21 min decay2_0
    val min_23          = min_22 min decay2_0
    val min_24          = min_23 min decay2_0
    val min_25          = min_24 min -0.0016006872
    val min_26          = min_25 min -0.0016006872
    val min_27          = min_26 min -0.0016006872
    val min_28          = min_27 min -0.0016006872
    val min_29          = min_28 min -0.0016006872
    val min_30          = min_29 min -0.0016006872
    val min_31          = min_30 min -0.0016006872
    val in_7            = Protect(-0.0016006872, -inf, inf, true)
    val maxDelayTime_0  = Protect(-0.0016006872, 0.0, 20.0, false)
    val protect_0       = Protect(min_31, 0.0, inf, false)
    val delayTime_0     = protect_0 min maxDelayTime_0
    val delayC          = DelayC.ar(in_7, maxDelayTime = maxDelayTime_0, delayTime = delayTime_0)
    val min_32          = delayC min 0.0012982563
    val min_33          = min_32 min 0.0012982563
    val b_1             = RunningSum.ar(2.5775092, length = 5464.677)
    val lFDNoise0_0     = LFDNoise0.ar(613.37524)
    val s_0             = lFDNoise0_0 hypot -1501.5878
    val in_9            = Protect(427.87445, -inf, inf, true)
    val hPZ1            = HPZ1.ar(in_9)
    val a_0             = Protect(syncSaw_0, -3.0, 3.0, false)
    val b_2             = Protect(367.87027, 0.5, 1.5, false)
    val c_0             = Protect(17.97094, 0.5, 1.5, false)
    val xi_1            = Protect(hPZ1, -inf, inf, false)
    val s_1             = LatoocarfianC.ar(freq = p5, a = a_0, b = b_2, c = c_0, d = 25.01335,
      xi = xi_1, yi = p7)
    val iphase_0        = 0.0011755856 < s_1
    val lFTri           = LFTri.ar(freq = p2, iphase = iphase_0)
    val min_38          = lPZ2_0 min lFTri
    val min_39          = min_38 min lFTri
    val b_3             = min_39 min lFTri
    val freq_4          = Protect(min_33, -inf, inf, false)
    val h_0             = Protect(0.3069152, 0.0, 0.06, false)
    val xi_2            = Protect(linCongN, -inf, inf, false)
    val yi_0            = Protect(decay, -inf, inf, false)
    val zi_0            = Protect(b_3, -inf, inf, false)
    val a_1             = LorenzL.ar(freq = freq_4, s = s_0, r = 0.007397973, b = b_3, h = h_0, xi = xi_2,
      yi = yi_0, zi = zi_0)
    val freq_5          = Protect(atan2, -inf, inf, false)
    val cuspL_0         = CuspL.ar(freq = freq_5, a = a_1, b = b_0, xi = p1)
    val freq_6          = Protect(cuspL_0, -inf, inf, false)
    val xi_3            = Protect(b_1, -inf, inf, false)
    val quadN           = QuadN.ar(freq = freq_6, a = 0.19546743, b = 0.3069152, c = 2.1631186, xi = xi_3)
    val a_2             = LFDClipNoise.ar(p5)
    val b_5             = Protect(-0.1538616, 0.5, 1.5, false)
    val c_1             = Protect(17.97094, 0.5, 1.5, false)
    val latoocarfianL_0 = LatoocarfianL.ar(freq = -1465.4102, a = a_2, b = b_5, c = c_1, d = 183076.34,
      xi = 1.0, yi = 0.3069152)
    val min_44          = p4 min leakDC_0
    val min_45          = min_44 min leakDC_0
    val min_46          = min_45 min leakDC_0
    val min_47          = min_46 min leakDC_0
    val min_48          = latoocarfianL_0 min quadN
    val min_49          = min_48 min quadN
    val min_50          = min_49 min quadN
    val in_14           = min_50 min quadN
    val min_51          = in_14 min quadN
    val min_52          = min_51 min quadN
    val min_53          = min_52 min quadN
    val min_54          = min_53 min quadN
    val min_55          = min_54 min quadN
    val min_56          = min_55 min quadN
    val in_15           = quadN min min_56
    val lFDNoise0_1     = LFDNoise0.ar(-7595.3853)
    val in_18           = Protect(b_0, -inf, inf, true)
    val delay2_0        = Delay2.ar(in_18)
    val in_19           = Protect(min_4, -inf, inf, true)
    val freq_12         = Protect(0.023090454, 10.0, 20000.0, false)
    val radius_1        = Protect(delay2_0, 0.0, 1.0, false)
    val twoPole_1       = TwoPole.ar(in_19, freq = freq_12, radius = radius_1)
    val in_20           = Protect(twoPole_1, -inf, inf, true)
    val bRZ2_0          = BRZ2.ar(in_20)
    val min_80          = min_47 min leakDC_0
    val min_81          = min_80 min leakDC_0
    val min_82          = min_81 min leakDC_0
    val min_83          = min_82 min leakDC_0
    val min_84          = min_83 min leakDC_0
    val min_85          = min_84 min leakDC_0
    val min_86          = min_85 min leakDC_0
    val min_87          = min_86 min leakDC_0
    val freq_13         = Protect(in_15, 0.01, 20000.0, false)
    val iphase_2        = Protect(-0.0016006872, 0.0, 1.0, false)
    val lFPar           = LFPar.ar(freq = freq_13, iphase = iphase_2)
    val hypotx          = lFPar hypotx lFDNoise0_1
    val syncFreq_0      = Protect(hypotx, 0.01, 20000.0, false)
    val sawFreq_0       = Protect(bRZ2_0, 0.01, 20000.0, false)
    val syncSaw_1       = SyncSaw.ar(syncFreq = syncFreq_0, sawFreq = sawFreq_0)
    val h_1             = Protect(atan2, 0.0, 0.06, false)
    val xi_7            = Protect(min_47, -inf, inf, false)
    val yi_2            = Protect(min_87, -inf, inf, false)
    val zi_1            = Protect(syncSaw_1, -inf, inf, false)
    val lorenzL         = LorenzL.ar(freq = Nyquist() /* could not parse! */, s = s_1, r = 0.016340986,
      b = b_1, h = h_1, xi = xi_7, yi = yi_2, zi = zi_1)
    val ring4_1         = lFTri ring4 p3
    val mix             = mkMix(
      runningSum_1,
      lorenzL,
      ring4_1,
    )
    NegatumOut(mix)
  }
}
