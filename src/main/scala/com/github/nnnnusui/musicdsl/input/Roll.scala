package com.github.nnnnusui.musicdsl.input

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

sealed trait Roll
object Roll {
  case class Create(division: Int) extends Roll
  case class GetAll() extends Roll
  case class GetById() extends Roll
  case class Update(division: Int) extends Roll
  trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val createInput = jsonFormat1(Create)
    implicit val updateInput = jsonFormat1(Update)
  }
}
