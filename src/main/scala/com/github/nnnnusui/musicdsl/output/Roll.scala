package com.github.nnnnusui.musicdsl.output

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

sealed trait Roll
object Roll {
  case class Create(id: Int, division: Int) extends Roll
  case class GetAll(values: Seq[Create]) extends Roll
  case class GetById(id: Int, division: Int) extends Roll
  case class Update(id: Int, division: Int) extends Roll
  trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val createOutput = jsonFormat2(Create)
    implicit val getAllOutput = jsonFormat1(GetAll)
    implicit val getByIdOutput = jsonFormat2(GetById)
    implicit val updateOutput = jsonFormat2(Update)
  }
}
