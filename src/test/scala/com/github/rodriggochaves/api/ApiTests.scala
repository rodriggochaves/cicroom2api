package com.github.rodriggochaves.api

import org.scalatra.test.scalatest._
import org.scalatest.FunSuiteLike

class ApiTests extends ScalatraSuite with FunSuiteLike {

  addServlet(classOf[Api], "/*")

  test("GET / on Api should return status 200"){
    get("/"){
      status should equal (200)
    }
  }

}
