package com.github.nnnnusui.musicdsl.repository

import com.github.nnnnusui.musicdsl.entity.score.{Roll => Entity}

import scala.concurrent.Future

trait Roll {
  val repository: Repository

  class Repository(implicit val usesDatabase: UsesDatabase) {
    val tableName = "roll"
    import usesDatabase._
    import profile.api._

    class TableInfo(tag: Tag) extends Table[Entity](tag, tableName) {
      def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
      def division = column[Int]("division")
      def * = (id, division) <> (Entity.tupled, Entity.unapply)
    }
    protected val tableQuery = TableQuery[TableInfo]
    protected def tableAutoInc = tableQuery returning tableQuery.map(_.id)
    def ddl = db.run { tableQuery.schema.create }

    def create(becomeEntity: Int => Entity): Future[Int] =
      db.run {
        tableQuery += becomeEntity(-1)
      }
    def update(entity: Entity): Future[Int] =
      db.run {
        tableQuery.filter(_.id === entity.id).update(entity)
      }
    def getById(id: Int): Future[Option[Entity]] =
      db.run {
        tableQuery.filter(_.id === id).result.headOption
      }
    def getAll: Future[List[Entity]] =
      db.run {
        tableQuery.to[List].result
      }
    def delete(id: Int): Future[Int] =
      db.run {
        tableQuery.filter(_.id === id).delete
      }
  }
}
