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
        val foldedNotes = (folded.store :+ Data(folded.pos, folded.length)).tail

        val channel = 1
        val length = sampleRate * roll.division / 4
        val beat = length / roll.division
        println(foldedNotes)
//        val note = notes.head
//        val offset = note.offset
//        val noteLength =
//          notes
//            .filter(_.octave == note.octave)
//            .filter(_.pitch == note.pitch)
//            .sortBy(_.offset)
//            .foldLeft(1) { case (sum, it) => if (it.sticky) sum + 1 else sum }
//        val hertz = getHertz(note.octave * maxPitch + note.pitch - keyA)
//        val note = foldedNotes.head
//        val offset = note.pos.offset
//        val noteLength = note.length
//        val hertz = getHertz(note.pos.octave * maxPitch + note.pos.pitch - keyA)
//        val pcm = generate(sampleRate, hertz, offset * beat, noteLength * beat)

        val pcm =
          foldedNotes
            .map(note => {
              val offset = note.pos.offset
              val noteLength = note.length
              val hertz = getHertz(note.pos.octave * maxPitch + note.pos.pitch - keyA)
              generate(sampleRate, hertz, offset * beat, noteLength * beat)
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
        Output.Get(channel, length, pcm)
      }
  }
}
