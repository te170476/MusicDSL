package com.github.nnnnusui.musicdsl.router

import spray.json.{
  AdditionalFormats,
  BasicFormats,
  CollectionFormats,
  JsonFormat,
  ProductFormats,
  RootJsonFormat,
  StandardFormats
}

// https://stackoverflow.com/a/50452140
trait FlexibleDefaultJsonProtocol
    extends BasicFormats
    with StandardFormats
    // with CollectionFormats
    with FlexibleCollectionFormats
    with ProductFormats
    with AdditionalFormats

object FlexibleDefaultJsonProtocol extends FlexibleDefaultJsonProtocol
trait FlexibleCollectionFormats extends CollectionFormats {

  implicit override def listFormat[T: JsonFormat] =
    new RootJsonFormat[List[T]] {
      import spray.json._

      def write(list: List[T]) = JsArray(list.map(_.toJson).toVector)
      def read(value: JsValue): List[T] =
        value match {
          case JsArray(elements) => elements.map(_.convertTo[T]).to(List)
          case JsString(element) => List[T](new JsString(element).convertTo[T])
          case x                 => deserializationError("Expected List as JsArray, but got " + x)
        }
    }
}
