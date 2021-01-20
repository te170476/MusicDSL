package com.github.nnnnusui.musicdsl.usecase

import com.github.nnnnusui.musicdsl.entity.score.{Note => Entity}
import com.github.nnnnusui.musicdsl.input.{Note => Input}
import com.github.nnnnusui.musicdsl.output.{Note => Output}
import com.github.nnnnusui.musicdsl.repository.{Note => Repository}

import scala.concurrent.{ExecutionContextExecutor, Future}

trait Note {
  this: Repository =>
  val useCase: UseCase

  implicit class InputCreateToEntity(it: Input.Create) {
    def toEntity(rollId: Int) = Entity(rollId, it.offset, it.octave, it.pitch)
  }
  implicit class EntityToOutput(it: Entity) {
    def toOutputCreate = Output.Create(it.offset, it.octave, it.pitch)
  }
  class UseCase(implicit val dispatcher: ExecutionContextExecutor) {
    def use(rollId: Int, input: Input.Create): Future[Output.Create] = {
      val entity = input.toEntity(rollId)
      repository
        .create(entity)
        .map(_ => entity.toOutputCreate)
    }
    def use(input: Input.GetAll): Future[Output.GetAll] =
      repository.getAll
        .map(_.map(_.toOutputCreate))
        .map(it => Output.GetAll(it))
  }
}
