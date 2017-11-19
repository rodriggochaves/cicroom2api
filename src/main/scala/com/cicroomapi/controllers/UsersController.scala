package com.cicroomapi.controllers

import org.scalatra._
import org.scalatra.json._
import org.json4s.JsonAST._
import org.json4s.{DefaultFormats, Formats}
import slick.jdbc.JdbcBackend.Database
import org.scalatra.CorsSupport

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


class UsersController(val db: Database) extends ScalatraServlet  
                                        with JacksonJsonSupport
                                        with CorsSupport {

  options("/*") {
    response.setHeader(
      "Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers")
    )
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"))
    response.setHeader("Access-Control-Allow-Methods", request.getHeader("POST"))
  }

  protected implicit lazy val jsonFormats: Formats = DefaultFormats
  
  post("/") { 
    val parameters = parsedBody.extract[Map[String, User]]
    UserModel.create(parameters("user").listParams)
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"))
    Response("ok")
  } 

  // post("/auth"){
  //   val parameters = parsedBody.extract[Map[String,UserAuth]]
  //   response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"))
  //   Response("ok")
  // }

  get("/select"){
    val q1 = TableSchema.roles.filter(p => p.description === "Aluno") .delete
  }

}
