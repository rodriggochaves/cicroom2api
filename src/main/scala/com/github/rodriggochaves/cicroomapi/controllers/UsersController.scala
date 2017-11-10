package com.github.rodriggochaves.cicroomapi

import org.scalatra._
import org.scalatra.json._
import org.json4s.JsonAST._
import org.json4s.{DefaultFormats, Formats}
import slick.driver.PostgresDriver.api._

case class User(username: String, email: String, password: String) {
  def listParams = (username, email, password)
}

class UsersController(val db: Database) extends ScalatraServlet  with JacksonJsonSupport{

  protected implicit lazy val jsonFormats: Formats = DefaultFormats
  
  post("/") {
    val user = parsedBody.extract[User]
    user
    // db.run(Schema.createUser(user.listParams))
    // user
  }

}
