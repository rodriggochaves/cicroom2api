package com.cicroomapi.controllers

import org.scalatra._
import slick.driver.PostgresDriver.api._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import _root_.akka.actor.ActorSystem

import com.cicroomapi.models.TableSchema

class ApplicationController(val db: Database, val system: ActorSystem)
  extends ScalatraServlet with FutureSupport with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats
  protected implicit def executor = system.dispatcher

  get("/db/drop") {
    db.run(TableSchema.dropSchemaAction)
      .map(_ => "ok")
  }

  get("/db/create") {
    db.run(TableSchema.createSchemaAction)
      .map(_ => "ok")
  }

}
