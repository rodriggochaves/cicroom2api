package com.cicroomapi.models

import org.scalatra._
import slick.driver.PostgresDriver.api._

object TableSchema {
  
  val rooms = TableQuery[RoomsTable]
  val users = TableQuery[UsersTable]

  val schema = rooms.schema ++ users.schema

  val dropSchemaAction = schema.drop
  val createSchemaAction = schema.create

  // def createUser = users.map( c => (c.username, c.email, c.digest_password) ) 
  // += ("rbonifacio", "rodrigo123@email.com", "password")
}