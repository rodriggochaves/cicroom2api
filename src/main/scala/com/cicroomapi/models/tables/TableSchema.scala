package com.cicroomapi.models.tables

import org.scalatra._
import slick.driver.PostgresDriver.api._
import com.cicroomapi.models.tables.RoomsTable
import com.cicroomapi.models.tables.RolesTable
import com.cicroomapi.models.tables.UsersTable
import com.cicroomapi.models.tables.QueueTable
import com.cicroomapi.models.RoleModel

object TableSchema {
  
  val rooms = TableQuery[RoomsTable]
  val roles = TableQuery[RolesTable]
  val users = TableQuery[UsersTable]
  val queue = TableQuery[QueueTable]

  val schema = rooms.schema ++ roles.schema ++ users.schema ++ queue.schema

  val dropSchemaAction = schema.drop
  val createSchemaAction = schema.create

  // def createUser = users.map( c => (c.username, c.email, c.digest_password) ) 
  // += ("rbonifacio", "rodrigo123@email.com", "password")

  def createRoles = RoleModel.create
}