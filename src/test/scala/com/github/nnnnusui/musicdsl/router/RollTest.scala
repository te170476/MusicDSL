package com.github.nnnnusui.musicdsl.router

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{ContentTypes, MessageEntity, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.nnnnusui.musicdsl.input.{Roll => Input}
import com.github.nnnnusui.musicdsl.repository.UsesDatabase
import com.github.nnnnusui.musicdsl.{H2Database, Router}
import org.scalatest.concurrent.ScalaFutures._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class RollTest extends AnyWordSpec with Matchers with ScalatestRouteTest with Input.JsonSupport {
  implicit val executionContext = system.dispatcher
  implicit val repositoryImpl: UsesDatabase = H2Database
  val routes = new Router().Roll.route

  "Roll.route" should {
    "return no users if no present (GET /users)" in {
      Get() ~> Route.seal(routes) ~> check {
        status should ===(StatusCodes.OK)
        entityAs[String] should ===(s"""{"values":[]}""")
      }
    }
    "be able to add entity (POST)" in {
      val create = Input.Create(24)
      val entity = Marshal(create).to[MessageEntity].futureValue
      val request = Post().withEntity(entity)

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`application/json`)
//        entityAs[String] should ===(s"${create.toJson}")
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

    val id = 1
    "{id}" should {
      val path = s"/$id"
//      "return user if exists (GET)" in {
//        Get(path) ~> Route.seal(routes) ~> check {
//          status should ===(StatusCodes.OK)
//          entityAs[String] should ===(s"""""")
//        }
//      }
      "not allowed (POST)" in {
        Post(path) ~> Route.seal(routes) ~> check {
          status should ===(StatusCodes.MethodNotAllowed)
        }
      }
      "not allowed (PUT)" in {
        Put(path) ~> Route.seal(routes) ~> check {
          status should ===(StatusCodes.MethodNotAllowed)
        }
      }
      "not allowed (PATCH)" in {
        Patch(path) ~> Route.seal(routes) ~> check {
          status should ===(StatusCodes.MethodNotAllowed)
        }
      }
      "not allowed (DELETE)" in {
        Delete(path) ~> Route.seal(routes) ~> check {
          status should ===(StatusCodes.MethodNotAllowed)
        }
      }
    }
  }
}
