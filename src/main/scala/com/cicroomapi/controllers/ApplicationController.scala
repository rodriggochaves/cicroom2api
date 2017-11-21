package com.cicroomapi.controllers

import org.scalatra._
import slick.driver.PostgresDriver.api._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

import com.cicroomapi.models.tables.TableSchema

class ApplicationController(val db: Database) extends ScalatraServlet 
                                              with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  get("/db/drop") {
    db.run(TableSchema.dropSchemaAction)
    "ok"
  }

  get("/db/create") {
    db.run(TableSchema.createSchemaAction)
    Thread.sleep(500)
    TableSchema.createRoles
    "ok"
  }

}
  