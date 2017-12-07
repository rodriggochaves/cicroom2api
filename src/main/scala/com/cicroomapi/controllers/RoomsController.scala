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
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Origin")
    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Methods", "POST, DELETE")
  }

  post("/") {
    val parameters = parsedBody.extract[Map[String, RoomParams]]
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin") )
    new AsyncResult { 
      val is: Future[_] = {
        RoomModel.create(parameters("room")) match {
          case Right(x) => {
            x.fold(
              err => ErrorResponse("Error", err),
              room => ResponseCreatedRoom(room)
            )
          }
          case Left(x) => Future{ ErrorResponse("Error", x) }
        }
      }

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

  get("/:id/users"){
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin") )
    new AsyncResult{
      val is: Future[_] = RoomModel.listUsers(params("id").toInt).fold(
        err => println(err),
        users => ResponseRoomUsers("ok",users)
      )
    }
  }

  post("/enter"){
    val parameters = parsedBody.extract[Map[String, QueueParams]]
    val origin = request.getHeader("Origin")
    val headers = Map("Access-Control-Allow-Origin" -> "*",
                              "Access-Control-Allow-Headers" -> "*") 
    new AsyncResult{
      val is: Future[_] = QueueModel.create(parameters("user")).fold(
        _ => Response("Error"),
        queue => {
          QueueModel.findByRoom(queue.roomId).fold(
            _ => Response("Error"),
            rooms => Ok(ResponseQueue("ok", queue.id, queue.roomId, rooms.length), headers)
          )
        }
      )
    }
  }

  get("/:roomId/queues/:id") {
    new AsyncResult {
      val is: Future[_] = {
        QueueModel.findByRoom(params("roomId").toInt).fold(
          _ => Response("Error"),
          queues => {
            val headers = Map("Access-Control-Allow-Origin" -> "*",
                              "Access-Control-Allow-Headers" -> "*") 
            val queue = queues.indexWhere( q => q.id == Some(params("id").toInt) )
            Ok(QueuePositionResponse( queue + 1 ), headers)
          }
        )
      }
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
