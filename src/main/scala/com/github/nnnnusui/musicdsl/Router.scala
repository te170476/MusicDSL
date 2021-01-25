package com.github.nnnnusui.musicdsl

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.github.nnnnusui.musicdsl.repository.UsesDatabase

import scala.concurrent.{ExecutionContextExecutor, Future}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.github.nnnnusui.musicdsl.entity.score.{Note, Roll}

class Router(implicit val dispatcher: ExecutionContextExecutor, implicit val repositoryImpl: UsesDatabase) {

  object Note extends usecase.Note with repository.Note with router.Note {
    val useCase = new UseCase
    val repository = new Repository
  }
  object Roll extends usecase.Roll with repository.Roll with router.Roll {
    val useCase = new UseCase
    val repository = new Repository
    override val noteRouter = Note.route
    override val soundRouter: Int => Route = Sound.route
  }
  object Sound extends usecase.Sound with router.Sound {
    val useCase = new UseCase
    override def getRoll(rollId: Int): Future[Option[Roll]] =
      Roll.repository.getById(rollId)
    override def getNotesFromRollId(rollId: Int): Future[Seq[Note]] =
      Note.repository.getByRollId(rollId)
  }
  for {
    it <- Seq(
      Roll.repository.ddl,
      Note.repository.ddl
    )
  } yield it

  val rest =
    pathEndOrSingleSlash {
      get { complete("version info") }
    } ~
      pathPrefix("1") {
        pathEndOrSingleSlash {
          get { complete("available") }
        } ~
          pathPrefix("rolls") { Roll.route }
      }
  val route =
    pathSingleSlash {
      get { getFromResource("index.html") }
    } ~
      cors() { pathPrefix("rest") { rest } }
}
