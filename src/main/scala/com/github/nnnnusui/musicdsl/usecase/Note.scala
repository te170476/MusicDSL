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
    def toBecomeEntity(rollId: Int) =
      Entity(rollId, _, it.offset, it.octave, it.pitch, it.length, it.childRollId)
  }
  implicit class EntityToOutput(it: Entity) {
    def toOutputCreate = Output.Create(it.id, it.offset, it.octave, it.pitch, it.length, it.childRollId)
  }
  class UseCase(implicit val dispatcher: ExecutionContextExecutor) {
    def use(rollId: Int, input: Input.Create): Future[Output.Create] = {
      val becomeEntity = input.toBecomeEntity(rollId)
      repository
        .create(rollId, becomeEntity)
        .map(id => becomeEntity(id).toOutputCreate)
    }
    def use(rollId: Int): Future[Output.GetAll] =
      repository
        .getAll(rollId)
        .map(_.map(_.toOutputCreate))
        .map(it => Output.GetAll(it))
    def use(input: Input.Delete): Future[Output.Delete] =
      repository
        .delete(input.rollId, input.id)
        .map(it => Output.Delete(it))
  }
}
