package com.github.nnnnusui.musicdsl.input

sealed trait Sound
object Sound {
  case class Get(sampleRate: Int, tempo: Double, beat: Double)
}
