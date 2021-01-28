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
  implicit class InputCreateToOptional(it: Input.Update) {
//    def toOptional =
//      Repository.Optional.tupled(Input.Update.unapply(it).get)
    def toEntity(rollId: Int) =
      Entity(rollId, it.id, it.offset, it.octave, it.pitch, it.length, it.childRollId)
  }
  implicit class EntityToOutput(it: Entity) {
    def toOutputCreate = Output.Create(it.id, it.offset, it.octave, it.pitch, it.length, it.childRollId)
    def toOutputUpdate = Output.Update(it.id, it.offset, it.octave, it.pitch, it.length, it.childRollId)
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

    def use(rollId: Int, input: Input.Update): Future[Output.Update] = {
      val entity = input.toEntity(rollId)
      repository
        .update(rollId, entity)
        .map(_ => entity.toOutputUpdate)
    }
    def use(rollId: Int, input: Input.Delete): Future[Output.Delete] =
      repository
        .delete(rollId, input.id)
        .map(it => Output.Delete(it))
  }
}
