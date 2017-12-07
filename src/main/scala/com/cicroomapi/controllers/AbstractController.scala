package com.cicroomapi.controllers

import org.scalatra._

trait AbstractController extends ScalatraServlet {
  def headers = Map("Access-Control-Allow-Origin" -> "*",
                    "Access-Control-Allow-Headers" -> "*")

  options("/*") {
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Origin")
    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Methods", "POST, DELETE")
  }

}