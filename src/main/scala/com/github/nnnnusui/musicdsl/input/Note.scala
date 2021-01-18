package com.github.nnnnusui.musicdsl.input

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

sealed trait Note
object Note {
  case class Create(offset: List[List[Int]]) extends Note
  trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val createFormat = jsonFormat1(Create)
  }
}
