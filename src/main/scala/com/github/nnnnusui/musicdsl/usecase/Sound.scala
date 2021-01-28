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

    def use(rollId: Int, input: Input.Get): Future[Output.Get] = {
      val channel = 1
      for (map <- RollNoteMap.getFuture(rollId)) yield {
        val length = input.sampleRate * 4
        val pcm = map.getPcm(input.sampleRate, 4)
        Output.Get(channel, length, pcm)
      }
    }

    object RollNoteMap {
      def getFuture(rollId: Int): Future[RollNoteMap] =
        getRollIdMap(rollId)
          .map(it => new RollNoteMap(rollId, it))
      def getRollIdMap(rollId: Int): Future[Map[Int, (Roll, Seq[Note])]] =
        getRoll(rollId).flatMap {
          case None => Future.failed(new Exception(s"rollId: $rollId _ not found on stack"))
          case Some(roll) =>
            getNotesFromRollId(rollId)
              .flatMap { notes =>
                val futures =
                  notes.map(_.childRollId match {
                    case None        => Future { Map.empty }
                    case Some(value) => getRollIdMap(value)
                  })
                Future
                  .sequence(futures)
                  .map(_.foldLeft(Map(rollId -> (roll, notes))) { case (sum, it) => sum ++ it })
              }
        }
    }
    class RollNoteMap private (val rootId: Int, val map: Map[Int, (Roll, Seq[Note])] = Map.empty) {
      def getPcm(sampleRate: Int, division: Int): Seq[Double] =
        getPcm(sampleRate, sampleRate * division, rootId, 0, 0)
      private def getPcm(sampleRate: Int, length: Int, rollId: Int, divisionSum: Int, offsetSum: Int): Seq[Double] = {
        val (roll, folded) = map(rollId)
        val division = roll.division
        val beat = length / division
        val maxPitch = 12

        folded
          .map(note => {
            val offset = offsetSum + note.offset * beat
            val length = note.length * beat
            val hertz = getHertz(note.octave * maxPitch + note.pitch)
            note.childRollId match {
              case Some(value) => getPcm(sampleRate, length, value, division, offset)
              case None        => generate(sampleRate, hertz, offset, length)
            }
          })
          .fold(Seq.empty[Double])((sumPcm, pcm) =>
            sumPcm
              .zipAll(pcm, 0.0, 0.0)
              .map {
                case (sum, it) =>
                  if (sum == 0.0) it
                  else if (it == 0.0) sum
                  else sum * 0.5 + it * 0.5
              }
          )
      }

      def getHertz(tone: Int): Double = {
        val keyA = 9
        val hertzReference = 440.0
        hertzReference * Math.pow(2, (tone - keyA) / 12.0)
      }
      def square(sampleRate: Int, index: Int, hertz: Double): Double = {
        val count = 2 * index * hertz / sampleRate
        if (count % 2 == 0) 1.0 else -1.0
      }
      def sin(sampleRate: Int, index: Int, hertz: Double) =
        Math.sin((2 * Math.PI * index * hertz) / sampleRate)
      def generate(sampleRate: Int, hertz: Double, offset: Int, length: Int): Seq[Double] = {
        Range(0, offset + length)
          .map(index => {
            if (index <= offset) 0.0
            else sin(sampleRate, index, hertz)
          })
      }
    }

  }

}
