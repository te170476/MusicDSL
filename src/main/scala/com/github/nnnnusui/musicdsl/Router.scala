package com.github.nnnnusui.musicdsl

import akka.http.scaladsl.server.Directives._
import com.github.nnnnusui.musicdsl.repository.UsesDatabase

import scala.concurrent.ExecutionContextExecutor

class Router(implicit val dispatcher: ExecutionContextExecutor, implicit val repositoryImpl: UsesDatabase) {
  object Note extends usecase.Note with repository.Note with router.Note {
    val useCase = new UseCase
    val repository = new Repository
  }
  for {
    it <- Seq(
      Note.repository.ddl
    )
  } yield it

  val rest =
    pathPrefix("rest") {
      pathEndOrSingleSlash {
        get { complete("version info") }
      } ~
        pathPrefix("1") {
          pathEndOrSingleSlash {
            get { complete("available") }
          } ~
            pathPrefix("notes") { Note.route }
        }
    }
  val route =
    pathSingleSlash {
      get { getFromResource("index.html") }
    } ~
      rest
}
