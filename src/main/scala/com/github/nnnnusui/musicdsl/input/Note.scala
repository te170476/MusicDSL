package com.github.nnnnusui.musicdsl.input

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.github.nnnnusui.musicdsl.router.FlexibleDefaultJsonProtocol

sealed trait Note
object Note {
  case class Create(offset: Int, octave: Int, pitch: Int, length: Int, childRollId: Option[Int]) extends Note
  case class GetAll() extends Note
  case class Update(id: Int, offset: Int, octave: Int, pitch: Int, length: Int, childRollId: Option[Int])
  case class Delete(id: Int) extends Note
  trait JsonSupport extends SprayJsonSupport with FlexibleDefaultJsonProtocol {
    implicit val createInput = jsonFormat5(Create)
    implicit val updateInput = jsonFormat6(Update)
  }
}
