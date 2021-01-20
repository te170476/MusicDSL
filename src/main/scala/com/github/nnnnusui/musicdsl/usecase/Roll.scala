package com.github.nnnnusui.musicdsl.usecase

import com.github.nnnnusui.musicdsl.entity.score.{Roll => Entity}
import com.github.nnnnusui.musicdsl.input.{Roll => Input}
import com.github.nnnnusui.musicdsl.output.{Roll => Output}
import com.github.nnnnusui.musicdsl.repository.{Roll => Repository}

import scala.concurrent.ExecutionContextExecutor

trait Roll {
  this: Repository =>
  val useCase: UseCase

  implicit class InputCreateToEntity(it: Input.Create) {
    def toBecomeEntity = Entity(_, it.division)
  }
  implicit class EntityToOutput(it: Entity) {
    def toOutputCreate = Output.Create(it.id, it.division)
  }
  class UseCase(implicit val dispatcher: ExecutionContextExecutor) {
    def use(input: Input.Create) = {
      val becomeEntity = input.toBecomeEntity
      repository
        .create(becomeEntity)
        .map(id => becomeEntity(id).toOutputCreate)
    }
    def use(input: Input.GetAll) =
      repository.getAll
        .map(_.map(_.toOutputCreate))
  }
}
