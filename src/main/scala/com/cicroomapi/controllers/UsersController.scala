package com.cicroomapi.controllers

import org.scalatra._
import org.scalatra.json._
import org.json4s.JsonAST._
import org.json4s.{DefaultFormats, Formats}
import slick.jdbc.JdbcBackend.Database
import org.scalatra.CorsSupport
import org.scalatra.FutureSupport

// async libs
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success, Try}
import _root_.akka.actor.ActorSystem
import dispatch._

import com.cicroomapi.models.UserModel
import com.cicroomapi.models.TableSchema
import com.cicroomapi.models.UsersTable
import slick.driver.PostgresDriver.api._
import slick.dbio.DBIOAction


case class Response(status: String)

case class User(username: String, email: String, password: String, role:Int) {
  def listParams = (username, email, password,role)
}

case class UserAuth(email:String,password:String){
  def getEmail = (email)
  def getPassword = (password)
}

class UsersController(val db: Database, val system: ActorSystem) extends ScalatraServlet  
                                                                 with JacksonJsonSupport
                                                                 with CorsSupport
                                                                 with FutureSupport {

  protected implicit def executor: ExecutionContext = system.dispatcher

  protected implicit lazy val jsonFormats: Formats = DefaultFormats
  
  options("/*") {
    response.setHeader(
      "Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers")
    )
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"))
    response.setHeader("Access-Control-Allow-Methods", request.getHeader("POST"))
  }

  
  post("/") {
    val parameters = parsedBody.extract[Map[String, User]]
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin") )
    new AsyncResult { 
      val is: Future[_] = UserModel.create(parameters("user").listParams).fold(
        _ => Response("Error"),
        _ => Response("ok")
      )
    }
  }
}
