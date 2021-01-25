package com.github.nnnnusui.musicdsl.router

import com.github.nnnnusui.musicdsl.input.{Note => Input}
import com.github.nnnnusui.musicdsl.output.{Note => Output}
import com.github.nnnnusui.musicdsl.usecase.{Note => UseCase}
import akka.http.scaladsl.server.{Directives, Route}

trait Note extends Directives with Input.JsonSupport with Output.JsonSupport {
  this: UseCase =>
  val route: Int => Route = (rollId: Int) => {
    val action = new Action(rollId)
    pathEndOrSingleSlash {
      get { action.getAll } ~
        post { action.create } ~
        delete { action.delete }
    }
  }
  class Action(rollId: Int) {
    def create =
      entity(as[Input.Create]) { input =>
        onComplete(useCase.use(rollId, List(input))) { result => complete(result.map(_.head)) }
      } ~
        entity(as[Seq[Input.Create]]) { inputs =>
          onComplete(useCase.use(rollId, inputs)) { result => complete(result) }
        }

    def getAll = onComplete(useCase.use(Input.GetAll())) { result => complete(result) }
    def delete =
      parameters("offset".as[Int], "octave".as[Int], "pitch".as[Int]) { (offset, octave, pitch) =>
        onComplete(useCase.use(Input.Delete(rollId, offset, octave, pitch))) { result => complete(result) }
      }
  }
}
