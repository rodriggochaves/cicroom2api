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
import com.cicroomapi.models.tables.TableSchema
import com.cicroomapi.models.tables.RoomsTable
import slick.driver.PostgresDriver.api._
import slick.dbio.DBIOAction

class RoomsController(val db: Database) extends ScalatraServlet  with JacksonJsonSupport {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats
  
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
}
