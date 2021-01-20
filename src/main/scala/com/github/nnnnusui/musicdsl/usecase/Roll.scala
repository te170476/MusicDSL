package com.github.nnnnusui.musicdsl.usecase

import com.github.nnnnusui.musicdsl.entity.score.{Roll => Entity}
import com.github.nnnnusui.musicdsl.input.{Roll => Input}
import com.github.nnnnusui.musicdsl.output.{Roll => Output}
import com.github.nnnnusui.musicdsl.repository.{Roll => Repository}

import scala.concurrent.{ExecutionContextExecutor, Future}

trait Roll {
  this: Repository =>
  val useCase: UseCase

  implicit class InputCreateToEntity(it: Input.Create) {
    def toBecomeEntity = Entity(_, it.division)
  }
  implicit class EntityToOutput(it: Entity) {
    def toOutputCreate = Output.Create(it.id, it.division)
    def toOutputGetById = Output.GetById(it.id, it.division)
  }
  class UseCase(implicit val dispatcher: ExecutionContextExecutor) {
    def use(input: Input.Create): Future[Output.Create] = {
      val becomeEntity = input.toBecomeEntity
      repository
        .create(becomeEntity)
        .map(id => becomeEntity(id).toOutputCreate)
    }
    def use(input: Input.GetAll): Future[Output.GetAll] =
      repository.getAll
        .map(_.map(_.toOutputCreate))
        .map(it => Output.GetAll(it))
    def use(input: Input.GetById): Future[Option[Output.GetById]] =
      repository
        .getById(input.id)
        .map(_.map(_.toOutputGetById))
  }
}
