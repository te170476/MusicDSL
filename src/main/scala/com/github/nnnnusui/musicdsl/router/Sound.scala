package com.github.nnnnusui.musicdsl.router

import akka.http.scaladsl.server.Directives
import com.github.nnnnusui.musicdsl.usecase.{Sound => UseCase}

trait Sound extends Directives {
  this: UseCase =>
  val route = (rollId: Int) => get { complete(s"not impl $rollId") }
}
