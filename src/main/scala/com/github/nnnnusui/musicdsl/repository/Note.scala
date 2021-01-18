package com.github.nnnnusui.musicdsl.repository

import com.github.nnnnusui.musicdsl.entity.score.note.{NoteValue, Note => Entity}

trait Note {
  implicit val usesDatabase: UsesDatabase
  import usesDatabase._
  import profile.api._

  class TableInfo(tag: Tag) extends Table[Entity](tag, "note") {
    def id = column[NoteValue]("offset")
    override def * = <>(Entity.apply, Entity.unapply)
  }
}
