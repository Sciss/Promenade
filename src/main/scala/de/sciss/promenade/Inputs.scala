/*
 *  Inputs.scala
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

object Inputs {
  final val inf = Float.PositiveInfinity

  def neg_9905128(): Unit = {
    NegatumIn()
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
    val sinOsc            = SinOsc.ar(freq = 5814.3823, phase = phase_0)
    val min_0             = lag3 min -0.03851429
    val min_1             = min_0 min -0.03851429
    val lFDNoise3         = LFDNoise3.ar(0.51179975)
    val min_2             = min_1 min lFDNoise3
    val min_3             = lFDNoise3 min min_2
    val min_4             = min_3 min lFDNoise3
    val in_2              = Protect(min_4, -inf, inf, true)
    val freq_4            = Protect(lFDNoise3, 10.0, 20000.0, false)
    val bRF               = BRF.ar(in_2, freq = freq_4, rq = 8.579682)
    val min_5             = bRF min lFDNoise3
    val min_6             = min_1 min -0.03851429
    val min_7             = min_6 min -0.03851429
    val ring4             = 2298.9329 ring4 sinOsc
    val freq_5            = Protect(min_7, 0.01, 20000.0, false)
    val iphase_1          = Protect(2298.9329, 0.0, 1.0, false)
    val lFPar_0           = LFPar.ar(freq = freq_5, iphase = iphase_1)
    val min_8             = min_7 min -0.03851429
    val henonC_1          = HenonC.ar(freq = Nyquist() /* could not parse! */, a = -3386.997, b = 33.599243,
      x0 = 0.0, x1 = 0.12704836)
    val iphase_2          = Protect(2298.9329, 0.0, 1.0, false)
    val lFPar_1           = LFPar.ar(freq = 2195.7336, iphase = iphase_2)
    val min_9             = lFPar_1 min -0.008055595
    val min_10            = min_9 min 0.2474362
    val min_11            = min_10 min henonC_1
    val min_12            = 4.236047E-5 min min_11
    val min_13            = min_12 min min_8
    val min_14            = min_13 min -0.03851429
    val min_15            = min_14 min -0.03851429
    val min_16            = min_15 min -0.03851429
    val min_17            = min_16 min -0.03851429
    val min_18            = min_17 min -0.03851429
    val min_19            = min_18 min -0.03851429
    val min_20            = min_19 min -0.03851429
    val min_21            = min_20 min -0.03851429
    val min_22            = min_21 min -0.03851429
    val min_23            = min_22 min -0.03851429
    val min_24            = min_23 min -0.03851429
    val min_25            = min_24 min -0.03851429
    val min_26            = min_25 min -0.03851429
    val in_3              = Protect(-0.03851429, -inf, inf, true)
    val maxDelayTime_1    = Protect(-0.03851429, 0.0, 20.0, false)
    val protect_1         = Protect(min_26, 0.0, inf, false)
    val delayTime_1       = protect_1 min maxDelayTime_1
    val combL_0           = CombL.ar(in_3, maxDelayTime = maxDelayTime_1, delayTime = delayTime_1,
      decayTime = -0.03851429)
    val absdif            = combL_0 absdif delayL
    val geq               = -0.008055595 >= henonC_0
    val min_27            = combL_0 min geq
    val min_28            = min_27 min geq
    val min_29            = min_28 min geq
    val min_30            = min_29 min geq
    val min_31            = min_30 min geq
    val min_32            = min_31 min geq
    val min_33            = min_32 min geq
    val min_34            = min_33 min geq
    val min_35            = min_34 min geq
    val min_36            = min_35 min geq
    val min_37            = min_36 min geq
    val min_38            = min_37 min geq
    val min_39            = min_38 min geq
    val min_40            = min_39 min geq
    val min_41            = min_40 min geq
    val min_42            = min_41 min geq
    val min_43            = min_42 min geq
    val min_44            = min_43 min geq
    val min_45            = min_44 min geq
    val min_46            = min_45 min geq
    val min_47            = min_46 min geq
    val in_4              = Protect(min_42, -inf, inf, true)
    val integrator_0      = Integrator.ar(in_4, coeff = 0.025150076)
    val coeff_0           = LFDNoise3.ar(0.51179975)
    val phase_1           = Protect(-4.9757495, 0.0, 1.0, false)
    val impulse_0         = Impulse.ar(freq = 0.94795537, phase = phase_1)
    val in_5              = Protect(impulse_0, -inf, inf, true)
    val oneZero           = OneZero.ar(in_5, coeff = coeff_0)
    val min_48            = 0.016423143 min oneZero
    val min_49            = coeff_0 min min_48
    val freq_6            = Protect(min_49, -inf, inf, false)
    val a_2               = Protect(delayL, -3.0, 3.0, false)
    val b_0               = Protect(0.15273203, 0.5, 1.5, false)
    val c_0               = Protect(0.0072869626, 0.5, 1.5, false)
    val d                 = LatoocarfianN.ar(freq = freq_6, a = a_2, b = b_0, c = c_0, d = -0.008055595,
      xi = 5814.3823, yi = geq)
    val min_50            = min_34 min geq
    val min_51            = min_50 min geq
    val min_52            = min_51 min geq
    val min_53            = min_52 min geq
    val in_6              = Protect(geq, -inf, inf, true)
    val winSize_0         = Protect(geq, 0.001, 2.0, false)
    val pitchRatio        = Protect(a_1, 0.0, 4.0, false)
    val pitchDispersion   = Protect(d, 0.0, 1.0, false)
    val protect_2         = Protect(min_53, 0.0, inf, false)
    val timeDispersion_0  = protect_2 min winSize_0
    val pitchShift_0      = PitchShift.ar(in_6, winSize = winSize_0, pitchRatio = pitchRatio,
      pitchDispersion = pitchDispersion, timeDispersion = timeDispersion_0)
    val in_7              = Protect(pitchShift_0, -inf, inf, true)
    val freq_7            = Protect(-0.03851429, 10.0, 20000.0, false)
    val radius_0          = Protect(-410.82947, 0.0, 1.0, false)
    val twoZero_0         = TwoZero.ar(in_7, freq = freq_7, radius = radius_0)
    val excess            = phase_0 excess pitchShift_0
    val in_8              = Protect(excess, -inf, inf, true)
    val freq_8            = Protect(-607.21533, 10.0, 20000.0, false)
    val radius_1          = Protect(a_0, 0.0, 1.0, false)
    val twoZero_1         = TwoZero.ar(in_8, freq = freq_8, radius = radius_1)
    val a_3               = Protect(twoZero_0, -3.0, 3.0, false)
    val b_1               = Protect(33.599243, 0.5, 1.5, false)
    val c_1               = Protect(-0.008055595, 0.5, 1.5, false)
    val yi_0              = Protect(twoZero_1, -inf, inf, false)
    val latoocarfianC_0   = LatoocarfianC.ar(freq = -607.21533, a = a_3, b = b_1, c = c_1, d = d,
      xi = -231.94966, yi = yi_0)
    val iphase_3          = Protect(latoocarfianC_0, 0.0, 1.0, false)
    val lFPar_2           = LFPar.ar(freq = 447.09863, iphase = iphase_3)
    val min_54            = 0.004442197 min lFPar_2
    val min_55            = min_54 min lFPar_2
    val min_56            = min_55 min lFPar_2
    val b_2               = -4.9757495 - min_56
    val min_57            = b_2 min min_56
    val min_58            = min_57 min min_56
    val min_59            = min_58 min min_56
    val min_60            = min_59 min min_56
    val min_61            = min_60 min min_12
    val in_9              = Protect(min_61, -inf, inf, true)
    val freq_9            = Protect(0.4486289, 10.0, 20000.0, false)
    val a_4               = LPF.ar(in_9, freq = freq_9)
    val min_62            = henonC_0 min a_4
    val min_63            = min_62 min a_4
    val min_64            = min_38 min min_63
    val min_65            = min_63 min min_64
    val min_66            = min_65 min min_63
    val min_67            = min_66 min min_63
    val min_68            = min_67 min min_63
    val ring2             = pitchShift_0 ring2 lag3
    val in_10             = Protect(33.599243, -inf, inf, true)
    val lPZ1              = LPZ1.ar(in_10)
    val in_11             = Protect(lPZ1, -inf, inf, true)
    val maxDelayTime_2    = Protect(-607.21533, 0.0, 20.0, false)
    val protect_3         = Protect(-607.21533, 0.0, inf, false)
    val delayTime_2       = protect_3 min maxDelayTime_2
    val combL_1           = CombL.ar(in_11, maxDelayTime = maxDelayTime_2, delayTime = delayTime_2,
      decayTime = 33.599243)
    val coeff_1           = Protect(100.24339, 0.8, 0.99, false)
    val leakDC_0          = LeakDC.ar(0.94795537, coeff = coeff_1)
    val min_69            = min_10 min leakDC_0
    val min_70            = min_61 min min_69
    val min_71            = min_70 min 0.2
    val min_72            = d min min_71
    val xi_1              = Protect(min_72, -inf, inf, false)
    val c_2               = LinCongC.ar(freq = 0.51179975, a = a_4, c = combL_1, m = 1.0, xi = xi_1)
    val lt_0              = 2.2159097 < excess
    val freq_10           = Protect(pitchShift_0, -inf, inf, false)
    val quadL             = QuadL.ar(freq = freq_10, a = combL_1, b = 0.2474362, c = c_2, xi = lt_0)
    val min_73            = quadL min a_0
    val in_12             = Protect(min_0, -inf, inf, true)
    val freq_11           = Protect(absdif, 10.0, 20000.0, false)
    val rq_0              = Protect(ring2, 0.01, 100.0, false)
    val rLPF_0            = RLPF.ar(in_12, freq = freq_11, rq = rq_0)
    val min_74            = min_73 min rLPF_0
    val min_75            = min_74 min a_0
    val min_76            = min_75 min 3.3873496
    val min_77            = henonC_0 min 5814.3823
    val min_78            = min_76 min min_77
    val min_79            = min_78 min min_63
    val min_80            = min_79 min min_63
    val min_81            = min_80 min min_63
    val min_82            = min_63 min a_4
    val min_83            = min_82 min a_4
    val min_84            = min_83 min a_4
    val min_85            = min_84 min a_4
    val min_86            = min_85 min a_4
    val min_87            = min_86 min a_4
    val min_88            = min_87 min a_4
    val min_89            = min_88 min a_4
    val min_90            = a_4 min min_89
    val min_91            = min_90 min a_4
    val min_92            = min_91 min a_4
    val min_93            = min_92 min a_4
    val min_94            = min_93 min a_4
    val min_95            = min_94 min a_4
    val min_96            = min_95 min a_4
    val min_97            = 0.2 min a_4
    val quadC             = QuadC.ar(freq = 0.10245416, a = 0.74982363, b = b_2, c = -4.9757495, xi = 0.0)
    val in_13             = Protect(min_96, -inf, inf, true)
    val maxDelayTime_3    = Protect(a_4, 0.0, 20.0, false)
    val protect_4         = Protect(min_97, 0.0, inf, false)
    val delayTime_3       = protect_4 min maxDelayTime_3
    val allpassN          = AllpassN.ar(in_13, maxDelayTime = maxDelayTime_3, delayTime = delayTime_3,
      decayTime = 0.10245416)
    val min_98            = allpassN min a_4
    val min_99            = min_98 min a_4
    val min_100           = min_99 min a_4
    val in_14             = Protect(oneZero, -inf, inf, true)
    val maxDelayTime_4    = Protect(a_4, 0.0, 20.0, false)
    val protect_5         = Protect(min_100, 0.0, inf, false)
    val delayTime_4       = protect_5 min maxDelayTime_4
    val decayTime_0       = CombL.ar(in_14, maxDelayTime = maxDelayTime_4, delayTime = delayTime_4,
      decayTime = -4.9757495)
    val in_15             = Protect(henonC_0, -inf, inf, true)
    val maxDelayTime_5    = Protect(-0.03851429, 0.0, 20.0, false)
    val protect_6         = Protect(-0.03851429, 0.0, inf, false)
    val delayTime_5       = protect_6 min maxDelayTime_5
    val combN             = CombN.ar(in_15, maxDelayTime = maxDelayTime_5, delayTime = delayTime_5,
      decayTime = decayTime_0)
    val bitXor            = d ^ ring2
    val coeff_2           = Protect(combN, 0.8, 0.99, false)
    val leakDC_1          = LeakDC.ar(0.011075321, coeff = coeff_2)
    val in_16             = Protect(leakDC_1, -inf, inf, true)
    val timeUp_0          = Protect(delayL, 0.0, 30.0, false)
    val timeDown_0        = Protect(-3386.997, 0.0, 30.0, false)
    val lagUD             = LagUD.ar(in_16, timeUp = timeUp_0, timeDown = timeDown_0)
    val mod               = 1007.1008 % lagUD
    val in_17             = Protect(0.94795537, -inf, inf, true)
    val freq_12           = Protect(0.016423143, 10.0, 20000.0, false)
    val rq_1              = Protect(mod, 0.01, 100.0, false)
    val bPF               = BPF.ar(in_17, freq = freq_12, rq = rq_1)
    val min_101           = bPF min bitXor
    val min_102           = min_101 min -0.03851429
    val neq               = absdif sig_!= combL_0
    val min_103           = min_102 min neq
    val min_104           = henonC_0 min min_56
    val earlyRefLevel     = min_104 min min_56
    val in_18             = Protect(min_35, -inf, inf, true)
    val protect_7         = Protect(min_81, 0.55, inf, false)
    val damping           = Protect(1200.5354, 0.0, 1.0, false)
    val inputBW           = Protect(min_103, 0.0, 1.0, false)
    val spread            = Protect(min_6, 0.0, 43.0, false)
    val maxRoomSize       = Protect(min_63, 0.55, 300.0, false)
    val roomSize          = protect_7 min maxRoomSize
    val gVerb             = GVerb.ar(in_18, roomSize = roomSize, revTime = 8.579682, damping = damping,
      inputBW = inputBW, spread = spread, dryLevel = 0.0032049967,
      earlyRefLevel = earlyRefLevel, tailLevel = 3.3873496, maxRoomSize = maxRoomSize)
    val in_19             = Protect(0.016423143, -inf, inf, true)
    val freq_13           = Protect(-231.94966, 10.0, 20000.0, false)
    val radius_2          = Protect(-607.21533, 0.0, 1.0, false)
    val twoPole           = TwoPole.ar(in_19, freq = freq_13, radius = radius_2)
    val freq_14           = Protect(min_17, -inf, inf, false)
    val xi_2              = Protect(gVerb, -inf, inf, false)
    val yi_1              = Protect(twoPole, -inf, inf, false)
    val gbmanL            = GbmanL.ar(freq = freq_14, xi = xi_2, yi = yi_1)
    val in_20             = Protect(twoPole, -inf, inf, true)
    val time_1            = Protect(pitchShift_0, 0.0, 30.0, false)
    val lag               = Lag.ar(in_20, time = time_1)
    val min_105           = min_77 min 5814.3823
    val fold2             = min_105 fold2 0.15273203
    val min_106           = min_105 min 5814.3823
    val min_107           = min_78 min min_106
    val in_21             = Protect(henonC_1, -inf, inf, true)
    val integrator_1      = Integrator.ar(in_21, coeff = 1.0)
    val amclip            = phase_0 amclip min_105
    val freq_15           = Protect(lFPar_1, -inf, inf, false)
    val a_5               = Protect(integrator_1, -3.0, 3.0, false)
    val c_3               = Protect(175.02394, 0.5, 1.5, false)
    val xi_3              = Protect(lFPar_2, -inf, inf, false)
    val yi_2              = Protect(amclip, -inf, inf, false)
    val latoocarfianL     = LatoocarfianL.ar(freq = freq_15, a = a_5, b = 3.0, c = c_3, d = 0.1369887,
      xi = xi_3, yi = yi_2)
    val min_108           = min_107 min latoocarfianL
    val min_109           = earlyRefLevel min min_56
    val min_110           = min_109 min min_56
    val min_111           = min_110 min min_56
    val min_112           = min_56 min min_111
    val min_113           = min_112 min min_56
    val min_114           = min_113 min min_56
    val min_115           = min_114 min min_56
    val min_116           = min_115 min min_56
    val min_117           = min_116 min min_56
    val min_118           = min_117 min min_56
    val min_119           = min_118 min min_56
    val min_120           = min_119 min min_56
    val in_22             = Protect(0.016423143, -inf, inf, true)
    val winSize_1         = Protect(min_56, 0.001, 2.0, false)
    val protect_8         = Protect(min_120, 0.0, inf, false)
    val timeDispersion_1  = protect_8 min winSize_1
    val pitchShift_1      = PitchShift.ar(in_22, winSize = winSize_1, pitchRatio = 0.7241163,
      pitchDispersion = 0.15273203, timeDispersion = timeDispersion_1)
    val a_6               = Protect(33.599243, -3.0, 3.0, false)
    val b_3               = Protect(henonC_1, 0.5, 1.5, false)
    val c_4               = Protect(0.15273203, 0.5, 1.5, false)
    val yi_3              = Protect(combN, -inf, inf, false)
    val latoocarfianC_1   = LatoocarfianC.ar(freq = -0.008055595, a = a_6, b = b_3, c = c_4, d = 0.011075321,
      xi = -410.82947, yi = yi_3)
    val min_121           = min_60 min min_56
    val freq_16           = Protect(d, -inf, inf, false)
    val xi_4              = Protect(c_2, -inf, inf, false)
    val linCongC          = LinCongC.ar(freq = freq_16, a = a_0, c = delayL, m = -3386.997, xi = xi_4)
    val in_23             = Protect(oneZero, -inf, inf, true)
    val hPZ2              = HPZ2.ar(in_23)
    val min_122           = min_48 min pitchShift_0
    val min_123           = min_49 min coeff_0
    val in_24             = Protect(5814.3823, -inf, inf, true)
    val freq_17           = Protect(0.0041119903, 10.0, 20000.0, false)
    val lPF               = LPF.ar(in_24, freq = freq_17)
    val freq_18           = Protect(lPF, -inf, inf, false)
    val lFDNoise0         = LFDNoise0.ar(freq_18)
    val sqrsum            = min_123 sqrsum lFDNoise0
    val in_25             = Protect(sqrsum, -inf, inf, true)
    val freq_19           = Protect(lPF, 10.0, 20000.0, false)
    val rLPF_1            = RLPF.ar(in_25, freq = freq_19, rq = 0.96277714)
    val min_124           = min_122 min rLPF_1
    val min_125           = -3.8409384E-5 min combN
    val min_126           = min_125 min 0.74982363
    val dur               = Protect(d, 5.0E-5, 100.0, false)
    val phase_2           = Protect(rLPF_1, 0.0, 1.0, false)
    val lFGauss           = LFGauss.ar(dur = dur, width = geq, phase = phase_2, loop = -3.8409384E-5,
      doneAction = doNothing)
    val roundTo           = min_32 roundTo 0.004442197
    val in_26             = Protect(bitXor, -inf, inf, true)
    val timeUp_1          = Protect(hPZ2, 0.0, 30.0, false)
    val timeDown_1        = Protect(linCongC, 0.0, 30.0, false)
    val lag2UD            = Lag2UD.ar(in_26, timeUp = timeUp_1, timeDown = timeDown_1)
    val length            = Protect(0.2, 1.0, 44100.0, false)
    val runningSum        = RunningSum.ar(lt_0, length = length)
    val in_27             = Protect(min_63, -inf, inf, true)
    val attack            = Protect(runningSum, 0.0, 30.0, false)
    val release           = Protect(min_8, 0.0, 30.0, false)
    val decay2            = Decay2.ar(in_27, attack = attack, release = release)
    val min_127           = min_103 min 0.2
    val min_128           = min_127 min 0.2
    val min_129           = min_128 min 0.2
    val min_130           = min_129 min 0.2
    val min_131           = min_130 min 0.2
    val min_132           = min_131 min 0.2
    val lt_1              = -4.9757495 < min_128
    val in_28             = Protect(coeff_0, -inf, inf, true)
    val bRZ2              = BRZ2.ar(in_28)
    val min_133           = 0.025150076 min min_56
    val phase_3           = Protect(lPF, 0.0, 1.0, false)
    val impulse_1         = Impulse.ar(freq = 3012.5898, phase = phase_3)
    val min_134           = impulse_1 min min_8
    val min_135           = min_134 min latoocarfianL
    val mix               = Mix(
      Seq[GE](min_5, ring4, lFPar_0, min_47, integrator_0, min_68, quadC, gbmanL, lag, fold2, min_108, pitchShift_1, latoocarfianC_1, min_121, min_124, min_126, lFGauss, roundTo, lag2UD, decay2, min_132, lt_1, bRZ2, min_133, min_135))
    NegatumOut(mix)
  }
}
