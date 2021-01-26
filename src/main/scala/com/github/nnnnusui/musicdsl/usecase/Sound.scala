package com.github.nnnnusui.musicdsl.usecase

import com.github.nnnnusui.musicdsl.entity.score.{Note, Roll}
import com.github.nnnnusui.musicdsl.input.{Sound => Input}
import com.github.nnnnusui.musicdsl.output.{Sound => Output}

import scala.concurrent.{ExecutionContextExecutor, Future}

trait Sound {
  val useCase: UseCase
  def getRoll(rollId: Int): Future[Option[Roll]]
  def getNotesFromRollId(rollId: Int): Future[Seq[Note]]

  case class Pos(offset: Int, octave: Int, pitch: Int)
  case class Data(pos: Pos, length: Int)
  case class Fold(store: Seq[Data], pos: Pos, length: Int, beforeSticky: Boolean)
  def getHertz(pitch: Int): Double = {
    val pitchReference = 440.0
    pitchReference * Math.pow(2, pitch / 12.0)
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
  class UseCase(implicit val dispatcher: ExecutionContextExecutor) {
    def use(rollId: Int, input: Input.Get): Future[Output.Get] =
      for {
        optRoll <- getRoll(rollId)
        notes <- getNotesFromRollId(rollId)
      } yield {
        val roll = optRoll match {
          case Some(value) => value
          case None        => return Future.failed(new Exception(""))
        }
        val folded =
          notes
            .sortBy(_.offset)
            .sortBy(_.pitch)
            .sortBy(_.octave)
            .foldLeft(Fold(Seq(), Pos(-1, -1, -1), 0, false))((fold, note) => {
              val samePitch =
                fold.pos.octave == note.octave &&
                  fold.pos.pitch == note.pitch
              if (samePitch && fold.beforeSticky) {
                val length = fold.length + 1
                Fold(fold.store, fold.pos, length, note.sticky)
              } else {
                val store = fold.store :+ Data(fold.pos, fold.length)
                val pos = Pos(note.offset, note.octave, note.pitch)
                Fold(store, pos, 1, note.sticky)
              }
            })

        val maxPitch = 12
        val keyA = 9
        val sampleRate = input.sampleRate
        val bar = 4
        val beat = sampleRate / bar
        val foldedNotes = (folded.store :+ Data(folded.pos, folded.length)).tail

        val channel = 1
        val length = sampleRate * roll.division / 4
        println(foldedNotes)
        val note = notes.head
        val hertz = getHertz(note.octave * maxPitch + note.pitch - keyA)
        val pcm =
          generate(sampleRate, hertz, 0, length)
//          Range(0, length)
//            .map(index => {
//              sin(sampleRate, index, 440 / 2)
//            })
//          foldedNotes
//            .map(note => {
//              val offset = beat * note.pos.offset
//              val length = beat * note.length
//              val hertz = getHertz(note.pos.octave * maxPitch + note.pos.pitch - keyA)
//              generate(sampleRate, hertz, offset, length)
//            })
//            .fold(Seq.fill(length)(0.0))((sumPcm, pcm) =>
//              sumPcm
//                .zip(pcm)
//                .map {
//                  case (sum, it) =>
//                    if (sum == 0.0) it
//                    else if (it == 0.0) sum
//                    else sum * 0.5 + it * 0.5
//                }
//            )
        Output.Get(channel, length, pcm)
      }
  }
}
