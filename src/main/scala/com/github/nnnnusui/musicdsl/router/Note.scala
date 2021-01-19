package com.github.nnnnusui.musicdsl.router

import com.github.nnnnusui.musicdsl.input.{Note => Input}
import com.github.nnnnusui.musicdsl.output.{Note => Output}
import com.github.nnnnusui.musicdsl.usecase.{Note => UseCase}

import akka.http.scaladsl.server.Directives

trait Note extends Directives with Input.JsonSupport with Output.JsonSupport {
  this: UseCase =>
  val route =
    pathEndOrSingleSlash {
      get { Action.getAll } ~
        post { Action.create }
    }
  object Action {
    def create =
      entity(as[Input.Create]) { input =>
        onComplete(useCase.use(input)) { result => complete(result) }
      }
    def getAll = onComplete(useCase.use(Input.GetAll())) { result => complete(result) }
  }
}
