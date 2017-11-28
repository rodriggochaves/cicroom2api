package com.cicroomapi.controllers

// lib import
import org.scalatra._
import org.scalatra.json._
import org.json4s.JsonAST._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.CorsSupport
import org.scalatra.FutureSupport

// async libs
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success, Try}
import _root_.akka.actor.ActorSystem
import dispatch._

// my imports
import com.cicroomapi.models.UserModel
import com.cicroomapi.models.UserParams

class AuthController(val system: ActorSystem) extends ScalatraServlet  
                                                                 with JacksonJsonSupport
                                                                 with CorsSupport
                                                                 with FutureSupport {

  protected implicit def executor: ExecutionContext = system.dispatcher

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  options("/*") {
    response.setHeader(
      "Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers")
    )
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"))
    response.setHeader("Access-Control-Allow-Methods", request.getHeader("POST"))
  }

  post("/") {
    val parameters = parsedBody.extract[Map[String, UserParams]]
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin") )
    new AsyncResult {  
      val is: Future[_] = UserModel.find( parameters("user").email ).fold(
        _ => Response("error"),
        user => Response("ok", user)
      ) 
    }
  }

}