package com.cicroomapi.controllers

import org.scalatra._
import org.scalatra.json._
import org.json4s.JsonAST._
import org.json4s.{DefaultFormats, Formats}
import slick.driver.PostgresDriver.api._
import _root_.akka.actor.ActorSystem

class RoomsController(val db: Database, val system: ActorSystem)
  extends ScalatraServlet with JacksonJsonSupport with FutureSupport {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats
  protected implicit def executor = system.dispatcher

  post("/") {
    val hello = "Hello"
    hello
  }
}
