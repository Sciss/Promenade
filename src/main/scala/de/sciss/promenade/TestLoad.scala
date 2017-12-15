package de.sciss.promenade

import de.sciss.synth.{Server, SynthDef}

object TestLoad {
  def main(args: Array[String]): Unit = {
    Server.run { s =>
      Input.seq.zipWithIndex.foreach { case (in, idx) =>
        val df = SynthDef(s"neg$idx")(in())
        import de.sciss.synth.Ops._
        df.play()
      }
      Thread.sleep(4000)
      s.queryCounts()
      Thread.sleep(4000)
      println(s.counts.peakCPU)
    }
  }
}
