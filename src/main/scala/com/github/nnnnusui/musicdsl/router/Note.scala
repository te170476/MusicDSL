package com.github.nnnnusui.musicdsl.router

import com.github.nnnnusui.musicdsl.input.{Note => Input}
import com.github.nnnnusui.musicdsl.output.{Note => Output}
import com.github.nnnnusui.musicdsl.usecase.{Note => UseCase}
import akka.http.scaladsl.server.{Directives, Route}

trait Note extends Directives with Input.JsonSupport with Output.JsonSupport {
  this: UseCase =>
  val routeFromRoot =
    get {
      onComplete(useCase.use()) { result => complete(result) }
    }
  val route: Int => Route = (rollId: Int) => {
    val action = new Action(rollId)
    pathEndOrSingleSlash {
      get { action.getAll } ~
        post { action.create }
    } ~
      pathPrefix(IntNumber) { id =>
        pathEndOrSingleSlash {
          post { action.update(id) } ~
            delete { action.delete(id) }
        }
      }
  }
  class Action(rollId: Int) {
    def create =
      entity(as[Input.Create]) { input =>
        onComplete(useCase.use(rollId, input)) { result => complete(result) }
      }
    def getAll =
      onComplete(useCase.use(rollId)) { result => complete(result) }
    def update(id: Int) =
      entity(as[Input.Update]) { input =>
        onComplete(useCase.use(rollId, input)) { result => complete(result) }
      }
    def delete(id: Int) =
      onComplete(useCase.use(rollId, Input.Delete(id))) { result => complete(result) }
  }
}
