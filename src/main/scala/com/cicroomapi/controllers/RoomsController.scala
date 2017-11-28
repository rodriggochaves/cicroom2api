package com.cicroomapi.controllers

// lib import
import org.scalatra._
import org.scalatra.json._
import org.json4s.JsonAST._
import org.json4s.{DefaultFormats, Formats}
import slick.jdbc.JdbcBackend.Database
import org.scalatra.CorsSupport
import org.scalatra.FutureSupport
import scala.concurrent.ExecutionContext.Implicits.global

// async libs
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success, Try}
import _root_.akka.actor.ActorSystem
import dispatch._

// my imports
import com.cicroomapi.models.UserModel
import com.cicroomapi.models.RoomParams
import com.cicroomapi.models.RoomModel
import com.cicroomapi.models.QueueModel
import com.cicroomapi.models.QueueParams
import com.cicroomapi.models.tables.TableSchema
import com.cicroomapi.models.tables.RoomsTable
import slick.driver.PostgresDriver.api._
import slick.dbio.DBIOAction
import _root_.akka.actor.ActorSystem


class RoomsController(val db: Database, val system: ActorSystem)
  extends ScalatraServlet with JacksonJsonSupport with FutureSupport {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats
  protected implicit def executor = system.dispatcher

  before() {
    contentType = formats("json")
  }

  post("/") {
    val parameters = parsedBody.extract[Map[String, RoomParams]]
    println(parameters)
    println(parameters("room").getDescription)
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin") )
    new AsyncResult { 
      val is: Future[_] = RoomModel.create(parameters("room")).fold(
        _ => Response("Error"),
        _ => Response("ok")
      )
    }
  }

  get("/"){
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin") )
    new AsyncResult{
      val is: Future[_] = RoomModel.list().fold(
        _ => ResponseRoom("Error"),
        rooms => ResponseRoom("ok",rooms)
      )
    }
  }

  post("/enter"){
    val parameters = parsedBody.extract[Map[String, QueueParams]]
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin") )
    new AsyncResult{
      val is: Future[_] = QueueModel.create(parameters("user")).fold(
        _ => Response("Error"),
        _ => Response("ok")
      )
    }
  }
}
