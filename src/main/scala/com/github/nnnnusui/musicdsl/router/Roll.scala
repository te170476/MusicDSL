package com.github.nnnnusui.musicdsl.router

import com.github.nnnnusui.musicdsl.input.{Roll => Input}
import com.github.nnnnusui.musicdsl.output.{Roll => Output}
import com.github.nnnnusui.musicdsl.usecase.{Roll => UseCase}
import akka.http.scaladsl.server.{Directives, Route}

trait Roll extends Directives with Input.JsonSupport with Output.JsonSupport {
  this: UseCase =>
  val note: Int => Route
  val route =
    pathEndOrSingleSlash {
      get { Action.getAll } ~
        post { Action.create }
    } ~
      pathPrefix(IntNumber) { id =>
        pathEndOrSingleSlash {
          get { Action.getById(id) }
        } ~
          pathPrefix("notes") { note(id) }
      }
  object Action {
    def create =
      entity(as[Input.Create]) { input =>
        onComplete(useCase.use(input)) { result => complete(result) }
      }
    def getAll = onComplete(useCase.use(Input.GetAll())) { result => complete(result) }
    def getById(id: Int) = onComplete(useCase.use(Input.GetById(id))) { result => complete(result) }
  }
}
