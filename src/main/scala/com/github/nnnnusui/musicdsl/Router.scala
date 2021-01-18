package com.github.nnnnusui.musicdsl

import akka.http.scaladsl.server.Directives._
import com.github.nnnnusui.musicdsl.repository.UsesDatabase

import scala.concurrent.ExecutionContextExecutor

class Router(
    implicit val dispatcher: ExecutionContextExecutor,
    implicit val repositoryImpl: UsesDatabase
) {

  val note = new router.Note {}

  val rest =
    pathPrefix("rest") {
      pathEndOrSingleSlash {
        get { complete("version info") }
      } ~
        pathPrefix("1") {
          pathEndOrSingleSlash {
            get { complete("available") }
          } ~
            pathPrefix("note") { note.route }
        }
    }
  val route =
    pathSingleSlash {
      get { getFromResource("index.html") }
    } ~
      rest
}
