package com.github.nnnnusui.musicdsl.output

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

sealed trait Note
object Note {
  case class Create(offset: Int, octave: Int, pitch: Int) extends Note
  case class GetAll(values: Seq[Create]) extends Note
  trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val createOutput = jsonFormat3(Create)
    implicit val getAllOutput = jsonFormat1(GetAll)
  }
}
