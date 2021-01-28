package com.github.nnnnusui.musicdsl.entity.score

case class Note(rollId: Int, id: Int, offset: Int, octave: Int, pitch: Int, length: Int, childRollId: Option[Int])
