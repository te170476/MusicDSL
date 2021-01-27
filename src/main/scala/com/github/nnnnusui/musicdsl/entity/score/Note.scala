package com.github.nnnnusui.musicdsl.entity.score

case class Note(rollId: Int, offset: Int, octave: Int, pitch: Int, sticky: Boolean, childRollId: Option[Int])
