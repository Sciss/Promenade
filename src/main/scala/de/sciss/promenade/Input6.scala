/*
 *  Input6.scala
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

object Input6 extends Input {
  import Promenade._

//  situations(0) = Vector(0.023866f, 5254.203125f, -0.003597f, 373.657745f, 7611.709473f, 4.118704f, 0.200000f, 0.032201f)
//  situations(1) = Vector(0.214000f, 54.202999f, -111.003998f, -1.658000f, 611.708984f, 3.119000f, 0.400000f, 0.062000f)

  situations(0) = Vector(0.023866f, 54.202999f, -0.003597f, 373.657745f, 7611.709473f, 3.119000f, -2.000000f, -0.032000f)
  situations(1) = Vector(0.214000f, 54.203125f, -111.003998f, -1.657990f, 11.709000f, 119.000000f, -11.000000f, 0.062000f)

  def apply(): Unit = { // aef822df
    // NegatumIn()
    val p1              = 0.023865523.!
    val p2              = 5254.203.!
    val p3              = -0.0035966213.!
    val p4              = 373.65775.!
    val p5              = 7611.7095.!
    val p6              = 4.1187043.!
    val p7              = 0.2.!
    val p8              = 0.032201175.!

    val in_0            = Protect(p1, -inf, inf, true)
    val lPZ2            = LPZ2.ar(in_0)
    val linCongL_0      = LinCongL.ar(freq = p1, a = p1, c = p1,
      m = p1, xi = 0.023865523)
    val min_0           = 0.003999994 min lPZ2
    val min_1           = min_0 min lPZ2
    val min_2           = min_1 min lPZ2
    val min_3           = min_2 min lPZ2
    val min_4           = min_3 min lPZ2
    val min_5           = min_4 min p7
    val min_6           = min_5 min p7
    val min_7           = min_6 min p7
    val min_8           = min_7 min p7
    val min_9           = min_8 min p7
    val min_10          = min_9 min p7
    val min_11          = min_4 min lPZ2
    val min_12          = min_11 min lPZ2
    val hypotx_0        = min_12 hypotx lPZ2
    val min_13          = hypotx_0 min lPZ2
    val min_14          = min_13 min lPZ2
    val min_15          = min_14 min 0.0066513135
    val min_16          = min_15 min 0.0066513135
    val min_19          = min_10 min p7
    val min_20          = min_19 min p7
    val min_21          = min_20 min p7
    val min_22          = min_21 min p7
    val min_23          = min_22 min p7
    val min_24          = min_23 min p7
    val min_25          = min_24 min p7
    val min_26          = min_25 min p7
    val min_27          = min_26 min p7
    val min_28          = min_27 min p7
    val min_29          = min_28 min p7
    val min_30          = min_29 min p7
    val min_31          = min_30 min p7
    val min_32          = min_31 min p7
    val in_2            = Protect(p4, -inf, inf, true)
    val protect_0       = Protect(min_32, 0.0, inf, false)
    val delayTime_0     = protect_0 min p7
    val in_3            = DelayC.ar(in_2, maxDelayTime = 0.2, delayTime = delayTime_0)
    val runningSum      = RunningSum.ar(in_3, length = 440.0)
    val in_4            = Protect(min_1, -inf, inf, true)
    val freq_0          = Protect(linCongL_0, 10.0, 20000.0, false)
    val radius          = Protect(min_16, 0.0, 1.0, false)
    val twoPole         = TwoPole.ar(in_4, freq = freq_0, radius = radius)
    val min_33          = linCongL_0 min twoPole
    val min_34          = min_33 min linCongL_0
    val min_35          = min_34 min linCongL_0
    val min_36          = min_35 min linCongL_0
    val min_37          = min_36 min linCongL_0
    val min_38          = min_37 min linCongL_0
    val min_39          = min_38 min linCongL_0
    val min_40          = min_39 min linCongL_0
    val min_41          = min_40 min linCongL_0
    val min_42          = min_41 min linCongL_0
    val min_43          = min_42 min linCongL_0
    val min_44          = min_43 min linCongL_0
    val min_45          = min_44 min linCongL_0
    val min_46          = min_45 min linCongL_0
    val in_5            = Protect(linCongL_0, -inf, inf, true)
    val maxDelayTime_0  = Protect(linCongL_0, 0.0, 20.0, false)
    val protect_1       = Protect(min_46, 0.0, inf, false)
    val delayTime_1     = protect_1 min maxDelayTime_0
    val combL           = CombL.ar(in_5, maxDelayTime = maxDelayTime_0, delayTime = delayTime_1,
      decayTime = p1)
    val iphase_0        = linCongL_0 > combL
    val lFPar           = LFPar.ar(freq = 440.0, iphase = iphase_0)
    val in_6            = Protect(combL, -inf, inf, true)
    val time_0          = Protect(lFPar, 0.0, 30.0, false)
    val decay_0         = Decay.ar(in_6, time = time_0)
    val min_47          = min_10 min decay_0
    val b_0             = min_47 min decay_0
    val min_48          = b_0 min decay_0
    val min_49          = runningSum min p3
    val min_50          = min_49 min p3
    val min_51          = min_50 min p3
    val min_52          = min_51 min p3
    val freq_1          = min_16 min min_52
    val min_53          = min_48 min decay_0
    val min_54          = min_10 min linCongL_0
    val min_55          = linCongL_0 min min_54
    val min_56          = runningSum min hypotx_0
    val min_59          = min_55 min linCongL_0
    val min_60          = min_59 min linCongL_0
    val min_61          = min_60 min linCongL_0
    val hypot           = min_33 hypot p3
    val min_62          = hypot min p3
    val min_63          = min_62 min p3
    val min_64          = min_63 min p3
    val min_65          = min_64 min p3
    val b_1             = min_65 min p3
    val in_7            = Protect(min_56, -inf, inf, true)
    val hPZ2_0          = HPZ2.ar(in_7)
    val in_8            = Protect(1.6506289, -inf, inf, true)
    val dur             = Protect(955.65466, 0.0, 30.0, false)
    val ramp            = Ramp.ar(in_8, dur = dur)
    val freq_3          = Protect(hPZ2_0, 0.01, 20000.0, false)
    val iphase_1        = Protect(ramp, -1.0, 1.0, false)
    val c_0             = LFSaw.ar(freq = freq_3, iphase = iphase_1)
    val xi_0            = Protect(min_53, -inf, inf, false)
    val quadC           = QuadC.ar(freq = 547.28076, a = -6.1463246, b = b_1, c = c_0, xi = xi_0)
    val in_9            = Protect(min_61, -inf, inf, true)
    val freq_4          = Protect(quadC, 10.0, 20000.0, false)
    val rq_0            = Protect(955.65466, 0.01, 100.0, false)
    val rHPF            = RHPF.ar(in_9, freq = freq_4, rq = rq_0)
    val in_10           = Protect(p8, -inf, inf, true)
    val freq_5          = Protect(-0.016387073, 10.0, 20000.0, false)
    val rq_1            = Protect(rHPF, 0.01, 100.0, false)
    val rLPF            = RLPF.ar(in_10, freq = freq_5, rq = rq_1)
    val in_11           = Protect(rLPF, -inf, inf, true)
    val freq_6          = Protect(-6.1463246, 10.0, 20000.0, false)
    val lPF             = LPF.ar(in_11, freq = freq_6)
    val k_0             = lPF hypot hPZ2_0
    val min_66          = p7 min k_0
    val min_67          = min_66 min p4
    val d_0             = min_67 min min_12
    val d_1             = LFDNoise1.ar(p1)
    val freq_7          = Protect(min_49, -inf, inf, false)
    val b_2             = Protect(iphase_0, 0.5, 1.5, false)
    val c_2             = Protect(min_7, 0.5, 1.5, false)
    val latoocarfianN   = LatoocarfianN.ar(freq = freq_7, a = p1, b = b_2, c = c_2, d = d_1,
      xi = -0.0035966213, yi = 0.023865523)
    val absdif_0        = latoocarfianN absdif decay_0
    val min_69          = absdif_0 min decay_0
    val in_13           = Protect(0.7123352, -inf, inf, true)
    val timeUp_0        = Protect(p4, 0.0, 30.0, false)
    val lagUD           = LagUD.ar(in_13, timeUp = timeUp_0, timeDown = 0.014667546)
    val min_70          = min_53 min decay_0
    val in_14           = Protect(min_69, -inf, inf, true)
    val freq_8          = Protect(lagUD, 10.0, 20000.0, false)
    val rq_2            = Protect(min_70, 0.01, 100.0, false)
    val b_3             = BRF.ar(in_14, freq = freq_8, rq = rq_2)
    val freq_9          = Protect(b_3, -inf, inf, false)
    val henonN          = HenonN.ar(freq = freq_9, a = p7, b = b_3, x0 = 373.65775, x1 = 547.28076)
    val wrap2           = 5.869442 wrap2 henonN
    val min_71          = -70.9547 min min_15
    val freq_10         = Protect(min_71, 0.01, 20000.0, false)
    val iphase_2        = Protect(min_70, 0.0, 1.0, false)
    val k_1             = LFPar.ar(freq = freq_10, iphase = iphase_2)
    val xi_2            = Protect(k_0, -inf, inf, false)
    val standardL       = StandardL.ar(freq = freq_1, k = k_1, xi = xi_2, yi = 0.02515013)
    val min_72          = standardL min min_36
    val freq_11         = Protect(min_47, -inf, inf, false)
    val a_0             = Protect(min_48, -3.0, 3.0, false)
    val c_3             = Protect(freq_1, 0.5, 1.5, false)
    val latoocarfianL_0 = LatoocarfianL.ar(freq = freq_11, a = a_0, b = 3.0, c = c_3, d = d_0, xi = 0.5,
      yi = 208.8177)
    val min_73          = twoPole min latoocarfianL_0
    val in_16           = Protect(3.6026387, -inf, inf, true)
    val time_2          = Protect(-1.7343903, 0.0, 30.0, false)
    val lag             = Lag.ar(in_16, time = time_2)
    val in_17           = Protect(lag, -inf, inf, true)
    val bRZ2            = BRZ2.ar(in_17)
    val min_74          = bRZ2 min rHPF
    val min_76          = min_74 min rHPF
    val a_1             = min_76 min rHPF
    val iphase_3        = Protect(min_15, 0.0, 1.0, false)
    val width           = Protect(linCongL_0, 0.0, 1.0, false)
    val varSaw          = VarSaw.ar(freq = p2, iphase = iphase_3, width = width)
    val in_18           = Protect(varSaw, -inf, inf, true)
    val hPZ1            = HPZ1.ar(in_18)
    val freq_13         = Protect(hPZ1, -inf, inf, false)
    val lFDNoise1       = LFDNoise1.ar(freq_13)
    val in_19           = Protect(decay_0, -inf, inf, true)
    val attack          = Protect(-70.9547, 0.0, 30.0, false)
    val release         = Protect(lFDNoise1, 0.0, 30.0, false)
    val decay2          = Decay2.ar(in_19, attack = attack, release = release)
    val min_81          = 208.8177 min henonN
    val min_82          = min_81 min henonN
    val min_83          = min_82 min henonN
    val min_84          = min_83 min henonN
    val min_85          = min_84 min henonN
    val min_86          = min_85 min henonN
    val min_87          = min_86 min henonN
    val min_88          = min_87 min henonN
    val in_20           = Protect(min_9, -inf, inf, true)
    val maxDelayTime_1  = Protect(henonN, 0.0, 20.0, false)
    val protect_2       = Protect(min_88, 0.0, inf, false)
    val delayTime_2     = protect_2 min maxDelayTime_1
    val delayC          = DelayC.ar(in_20, maxDelayTime = maxDelayTime_1, delayTime = delayTime_2)
    val min_89          = min_73 min delayC
    val min_90          = min_89 min min_1
    val in_21           = Protect(p5, -inf, inf, true)
    val timeUp_1        = Protect(-70.9547, 0.0, 30.0, false)
    val timeDown_0      = Protect(decay2, 0.0, 30.0, false)
    val c_5             = LagUD.ar(in_21, timeUp = timeUp_1, timeDown = timeDown_0)
    val min_91          = min_90 min wrap2
    val min_93          = min_91 min p3
    val min_94          = min_93 min p3
    val min_95          = min_94 min p3
    val excess          = min_95 excess p3
    val min_96          = excess min p3
    val min_97          = min_96 min p3
    val min_98          = min_97 min p3
    val in_22           = Protect(ramp, -inf, inf, true)
    val maxDelayTime_2  = Protect(p3, 0.0, 20.0, false)
    val protect_3       = Protect(min_98, 0.0, inf, false)
    val delayTime_3     = protect_3 min maxDelayTime_2
    val m_0             = CombC.ar(in_22, maxDelayTime = maxDelayTime_2, delayTime = delayTime_3,
      decayTime = 47.857548)
    val freq_14         = Protect(wrap2, -inf, inf, false)
    val xi_4            = Protect(c_5, -inf, inf, false)
    val linCongL_1      = LinCongL.ar(freq = freq_14, a = 547.28076, c = c_5, m = m_0, xi = xi_4)
    val xi_5            = Protect(k_0, -inf, inf, false)
    val yi_1            = Protect(combL, -inf, inf, false)
    val b_5             = StandardL.ar(freq = 5.869442, k = k_0, xi = xi_5, yi = yi_1)
    val x0_0            = Protect(min_49, -inf, inf, false)
    val x1_0            = Protect(min_74, -inf, inf, false)
    val henonL          = HenonL.ar(freq = Nyquist() /* could not parse! */, a = a_1, b = b_5, x0 = x0_0,
      x1 = x1_0)
    val min_99          = min_67 min henonL
    val min_100         = min_99 min henonL
    val absdif_1        = min_100 absdif henonL
    val in_24           = Protect(p4, -inf, inf, true)
    val hPZ2_1          = HPZ2.ar(in_24)
    val freq_15         = Protect(in_3, 0.01, 20000.0, false)
    val phase_1         = Protect(hPZ2_1, -inf, inf, false)
    val sinOsc          = SinOsc.ar(freq = freq_15, phase = phase_1)
    val amclip          = varSaw amclip p6
    val bitXor          = amclip ^ c_0
    val mix = mkMix(
      min_72,
      linCongL_1,
      absdif_1,
      sinOsc,
      bitXor
    )
    NegatumOut(mix)
  }
}