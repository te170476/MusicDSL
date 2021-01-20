package com.github.nnnnusui.musicdsl.output

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

sealed trait Roll
object Roll {
  case class Create(id: Int, division: Int) extends Roll
  case class GetAll(values: Seq[Create]) extends Roll
  trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val createOutput = jsonFormat2(Create)
    implicit val getAllOutput = jsonFormat1(GetAll)
  }
}
