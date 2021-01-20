package com.github.nnnnusui.musicdsl.input

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

sealed trait Roll
object Roll {
  case class Create(division: Int) extends Roll
  case class GetAll() extends Roll
  case class GetById(id: Int) extends Roll
  trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val createInput = jsonFormat1(Create)
    implicit val getByIdInput = jsonFormat1(GetById)
  }
}
