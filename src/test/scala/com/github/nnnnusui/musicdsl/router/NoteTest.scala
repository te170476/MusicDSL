package com.github.nnnnusui.musicdsl.router

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{ContentTypes, MessageEntity, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.nnnnusui.musicdsl.{H2Database, Router}
import com.github.nnnnusui.musicdsl.input.{Note => Input}
import com.github.nnnnusui.musicdsl.repository.UsesDatabase
import org.scalatest.concurrent.ScalaFutures._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import spray.json.enrichAny

class NoteTest extends AnyWordSpec with Matchers with ScalatestRouteTest with Input.JsonSupport {
  implicit val executionContext = system.dispatcher
  implicit val repositoryImpl: UsesDatabase = H2Database
  val routes = new Router().Note.route(1)

  "Note.route" should {
    "return no users if no present (GET /users)" in {
      Get() ~> Route.seal(routes) ~> check {
        status should ===(StatusCodes.OK)
        entityAs[String] should ===(s"""{"values":[]}""")
      }
    }
    "be able to add entity (POST)" in {
      val create = Input.Create(1, 2, 3)
      val entity = Marshal(create).to[MessageEntity].futureValue
      val request = Post().withEntity(entity)

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`application/json`)
        entityAs[String] should ===(s"${create.toJson}")
      }
    }
    "not allowed (PUT)" in {
      Put() ~> Route.seal(routes) ~> check {
        status should ===(StatusCodes.MethodNotAllowed)
      }
    }
    "not allowed (PATCH)" in {
      Patch() ~> Route.seal(routes) ~> check {
        status should ===(StatusCodes.MethodNotAllowed)
      }
    }
    "not allowed (DELETE)" in {
      Delete() ~> Route.seal(routes) ~> check {
        status should ===(StatusCodes.MethodNotAllowed)
      }
    }
  }
}
