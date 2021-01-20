package com.github.nnnnusui.musicdsl

import akka.http.scaladsl.server.Directives._
import com.github.nnnnusui.musicdsl.repository.UsesDatabase

import scala.concurrent.ExecutionContextExecutor
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

class Router(implicit val dispatcher: ExecutionContextExecutor, implicit val repositoryImpl: UsesDatabase) {
  object Roll extends usecase.Roll with repository.Roll with router.Roll {
    val useCase = new UseCase
    val repository = new Repository
  }
  object Note extends usecase.Note with repository.Note with router.Note {
    val useCase = new UseCase
    val repository = new Repository
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
          pathPrefix("rolls") { Roll.route } ~
          pathPrefix("notes") { Note.route }
      }
  val route =
    pathSingleSlash {
      get { getFromResource("index.html") }
    } ~
      cors() { pathPrefix("rest") { rest } }
}
