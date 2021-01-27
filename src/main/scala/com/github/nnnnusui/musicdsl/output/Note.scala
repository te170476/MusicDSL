package com.github.nnnnusui.musicdsl.output

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

sealed trait Note
object Note {
  case class Create(offset: Int, octave: Int, pitch: Int, sticky: Boolean, childRollId: Option[Int]) extends Note
  case class GetAll(values: Seq[Create]) extends Note
  case class Delete(amount: Int)
  trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val createOutput = jsonFormat5(Create)
    implicit val getAllOutput = jsonFormat1(GetAll)
    implicit val deleteOutput = jsonFormat1(Delete)
  }
}
