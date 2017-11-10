package com.github.rodriggochaves.cicroomapi

import org.scalatra._
import org.scalatra.json._
import org.json4s.JsonAST._
import org.json4s.{DefaultFormats, Formats}
import slick.driver.PostgresDriver.api._

class RoomsController(val db: Database) extends ScalatraServlet  with JacksonJsonSupport {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats
  
  post("/") {
    val hello = "Hello"
    hello
  }
}
