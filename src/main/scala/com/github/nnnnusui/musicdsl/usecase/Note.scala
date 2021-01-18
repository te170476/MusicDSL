package com.github.nnnnusui.musicdsl.usecase

import com.github.nnnnusui.musicdsl.entity.score.note.NoteValue
import com.github.nnnnusui.musicdsl.input.{Note => Input}
object Note {
  def use(input: Input.Create) = {
    val offset = input.offset
      .map(
        _.foldRight(NoteValue(1)) { (it, sum) => sum / it }
      )
      .foldLeft(NoteValue(0))(_ + _)
    println(offset.percentage)
  }
}
