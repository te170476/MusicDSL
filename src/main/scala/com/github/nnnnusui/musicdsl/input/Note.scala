package com.github.nnnnusui.musicdsl.input

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.github.nnnnusui.musicdsl.router.FlexibleDefaultJsonProtocol

sealed trait Note
object Note {
  case class Create(offset: Int, octave: Int, pitch: Int) extends Note
  case class GetAll() extends Note
  case class Delete(rollId: Int, offset: Int, octave: Int, pitch: Int) extends Note
  trait JsonSupport extends SprayJsonSupport with FlexibleDefaultJsonProtocol {
    implicit val createInput = jsonFormat3(Create)
    implicit val deleteInput = jsonFormat4(Delete)
  }
}
