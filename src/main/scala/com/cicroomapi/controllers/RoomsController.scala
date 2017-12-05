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

  options("/*") {
    println("recebi um OPTION")
    response.setHeader("Access-Control-Allow-Headers", "Content-Type")
    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Methods", "*")
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
        err => println(err),
        rooms => {
          val viewRooms = rooms.map( r => ViewRoom(r._1, r._2, r._3) )
          ResponseRoom("ok", viewRooms )
        }
      )
    }
  }

  post("/enter"){
    val parameters = parsedBody.extract[Map[String, QueueParams]]
    val origin = request.getHeader("Origin")
    new AsyncResult{
      val is: Future[_] = QueueModel.create(parameters("user")).fold(
        _ => Response("Error"),
        room => {
          var c = room.length
          // ResponseQueue("ok", room(0).roomId, c)
          val headers = Map("Access-Control-Allow-Origin" -> "*",
                            "Access-Control-Allow-Headers" -> "*") 
          Created(ResponseQueue("ok", room(0).roomId, c), headers)
        }
      )
    }
  }

  delete("/:id"){
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin") )
    new AsyncResult{
      val is: Future[_] = RoomModel.delete(params("id").toInt).fold(
        _ => Response("Error"),
        _ => Response("ok")
      )
    }
  }

  delete("/exit/:id"){
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin") )
    new AsyncResult{
      val is: Future[_] = QueueModel.delete(params("id").toInt).fold(
        _ => Response("Error"),
        _ => Response("ok")
      )
    }
  }
}
