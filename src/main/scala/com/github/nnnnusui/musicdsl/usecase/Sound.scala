package com.github.nnnnusui.musicdsl.usecase

import com.github.nnnnusui.musicdsl.entity.score.{Note, Roll}
import com.github.nnnnusui.musicdsl.input.{Sound => Input}
import com.github.nnnnusui.musicdsl.output.{Sound => Output}

import scala.concurrent.{ExecutionContextExecutor, Future}

trait Sound {
  val useCase: UseCase
  def getRoll(rollId: Int): Future[Option[Roll]]
  def getNotesFromRollId(rollId: Int): Future[Seq[Note]]

  class UseCase(implicit val dispatcher: ExecutionContextExecutor) {
    def use(rollId: Int, input: Input.Get): Future[Output.Get] =
      getRoll(rollId).map(roll => {
        val notes = getNotesFromRollId(rollId)
        val sampleRate = input.sampleRate
        val channel = 1
        val length = sampleRate * 5
        val pcm =
          Range(0, length)
            .map(index => {
              //            Math.sin((2 * Math.PI * index * 440) / sampleRate)
              val count = 2 * index * 440 / sampleRate
              if (count % 2 == 0) 1.0 else -1.0
            })
        Output.Get(channel, length, pcm)
      })
  }
}
