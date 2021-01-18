package com.github.nnnnusui.musicdsl.router

import akka.http.scaladsl.server.Directives
import com.github.nnnnusui.musicdsl.input.{Note => Input}
import com.github.nnnnusui.musicdsl.usecase.{Note => UseCase}

trait Note extends Directives with Input.JsonSupport {
  import UseCase.use
  val route =
    pathEndOrSingleSlash {
      post {
        entity(as[Input.Create]) { it =>
          use(it)
          complete(it)
        }
      }
    }
}
