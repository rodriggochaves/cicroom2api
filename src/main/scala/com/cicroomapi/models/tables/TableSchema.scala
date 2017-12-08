package com.cicroomapi.models.tables

import org.scalatra._
import slick.driver.PostgresDriver.api._

object TableSchema {
  
  val rooms = TableQuery[RoomsTable]
  val queue = TableQuery[QueueTable]

  val schema = rooms.schema ++ queue.schema

  val dropSchemaAction = schema.drop
  val createSchemaAction = schema.create
}