package com.github.nnnnusui.musicdsl.repository

import com.github.nnnnusui.musicdsl.entity.score.{Note => Entity}

import scala.concurrent.Future

trait Note {
  val repository: Repository

  class Repository(implicit val usesDatabase: UsesDatabase) {
    val tableName = "note"
    import usesDatabase._
    import profile.api._

    class TableInfo(tag: Tag) extends Table[Entity](tag, tableName) {
      def pk = primaryKey(s"pk_$tableName", (rollId, id))
      def rollId = column[Int]("roll_id")
      def id = column[Int]("id", O.AutoInc)
      def offset = column[Int]("offset")
      def octave = column[Int]("octave")
      def pitch = column[Int]("pitch")
      def length = column[Int]("length")
      def childRollId = column[Option[Int]]("child_roll_id")
      def * = (rollId, id, offset, octave, pitch, length, childRollId) <> (Entity.tupled, Entity.unapply)
    }
    protected val tableQuery = TableQuery[TableInfo]
    protected def tableAutoInc = tableQuery returning tableQuery.map(_.id)
    protected def find(from: Entity): Query[TableInfo, Entity, Seq] =
      find(from.rollId, from.id)
    protected def find(rollId: Int, id: Int): Query[TableInfo, Entity, Seq] =
      tableQuery
        .filter(_.rollId === rollId)
        .filter(_.id === id)
    def ddl = db.run { tableQuery.schema.create }

    def create(rollId: Int, becomeEntity: Int => Entity): Future[Int] =
      db.run {
        tableAutoInc += becomeEntity(-1)
      }

    def update(rollId: Int, entity: Entity): Future[Int] =
      db.run {
        find(rollId, entity.id).update(entity)
      }
    def getByKeys(rollId: Int, id: Int): Future[Option[Entity]] =
      db.run {
        find(rollId, id).result.headOption
      }
    def getAll(): Future[List[Entity]] =
      db.run {
        tableQuery
          .to[List]
          .result
      }
    def getFromRollId(rollId: Int): Future[List[Entity]] =
      db.run {
        tableQuery
          .filter(_.rollId === rollId)
          .to[List]
          .result
      }
    def delete(rollId: Int, id: Int): Future[Int] =
      db.run {
        find(rollId, id).delete
      }

    def getByRollId(rollId: Int): Future[Seq[Entity]] =
      db.run {
        tableQuery.filter(_.rollId === rollId).result
      }
  }
}
