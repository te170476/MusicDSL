package com.github.nnnnusui.musicdsl.repository

import com.github.nnnnusui.musicdsl.entity.score
import com.github.nnnnusui.musicdsl.entity.score.{Note => Entity}

import scala.concurrent.Future

trait Note {
  val repository: Repository

  class Repository(implicit val usesDatabase: UsesDatabase) {
    val tableName = "note"
    import usesDatabase._
    import profile.api._

    class TableInfo(tag: Tag) extends Table[Entity](tag, tableName) {
      def pk = primaryKey(s"pk_$tableName", (offset, octave, pitch))
      def rollId = column[Int]("roll_id")
      def offset = column[Int]("offset")
      def octave = column[Int]("octave")
      def pitch = column[Int]("pitch")
      def sticky = column[Boolean]("sticky")
      def * = (rollId, offset, octave, pitch, sticky) <> (Entity.tupled, Entity.unapply)
    }
    protected val tableQuery = TableQuery[TableInfo]
    protected def find(from: Entity): Query[TableInfo, Entity, Seq] =
      find(from.rollId, from.offset, from.octave, from.pitch)
    protected def find(rollId: Int, offset: Int, octave: Int, pitch: Int): Query[TableInfo, Entity, Seq] =
      tableQuery
        .filter(_.rollId === rollId)
        .filter(_.offset === offset)
        .filter(_.octave === octave)
        .filter(_.pitch === pitch)
    def ddl = db.run { tableQuery.schema.create }

    def create(entity: Entity): Future[Int] =
      db.run {
        tableQuery += entity
      }
    def creates(entities: Seq[Entity]): Future[Option[Int]] =
      db.run {
        tableQuery ++= entities
      }
    def update(entity: Entity): Future[Int] =
      db.run {
        find(entity).update(entity)
      }
    def getByKeys(rollId: Int, offset: Int, octave: Int, pitch: Int): Future[Option[Entity]] =
      db.run {
        find(rollId, offset, octave, pitch).result.headOption
      }
    def getAll: Future[List[Entity]] =
      db.run {
        tableQuery.to[List].result
      }
    def delete(rollId: Int, offset: Int, octave: Int, pitch: Int): Future[Int] =
      db.run {
        find(rollId, offset, octave, pitch).delete
      }

    def getByRollId(rollId: Int): Future[Seq[Entity]] =
      db.run {
        tableQuery.filter(_.rollId === rollId).result
      }
  }
}
