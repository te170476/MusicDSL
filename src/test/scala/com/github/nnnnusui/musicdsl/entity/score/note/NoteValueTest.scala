package com.github.nnnnusui.musicdsl.entity.score.note

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class NoteValueTest extends AnyWordSpec with Matchers {
  private val whole = NoteValue(1)
  private val triplet = NoteValue(3) / whole
  private val nonuplet = NoteValue(9) / whole

  "NoteValue" should {
    "3.base(3) equals 9" in {
      (triplet / triplet).percentage should ===(nonuplet.percentage)
    }
    "(9, 9, 9) equals 3" in {
      (nonuplet * 3).percentage should ===(triplet.percentage)
    }
  }
}
