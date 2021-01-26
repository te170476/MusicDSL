package com.github.nnnnusui.musicdsl.router

import com.github.nnnnusui.musicdsl.usecase.{Sound => UseCase}
import com.github.nnnnusui.musicdsl.output.{Sound => Output}

import akka.http.scaladsl.server.Directives

trait Sound extends Directives with Output.JsonSupport {
  this: UseCase =>
  val route = (rollId: Int) => get { onComplete(useCase.use(rollId)) { result => complete(result) } }
}
