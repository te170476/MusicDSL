package com.github.nnnnusui.musicdsl.router

import com.github.nnnnusui.musicdsl.usecase.{Sound => UseCase}
import com.github.nnnnusui.musicdsl.input.{Sound => Input}
import com.github.nnnnusui.musicdsl.output.{Sound => Output}

import akka.http.scaladsl.server.Directives

trait Sound extends Directives with Output.JsonSupport {
  this: UseCase =>
  val route = (rollId: Int) =>
    get {
      parameters("sample_rate".as[Int], "tempo".as[Double], "beat".as[Double]) { (sampleRate, tempo, beat) =>
        onComplete(useCase.use(rollId, Input.Get(sampleRate, tempo, beat))) { result => complete(result) }
      }
    }
}
