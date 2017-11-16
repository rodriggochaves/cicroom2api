package com.cicroomapi.models

import slick.driver.PostgresDriver.api._

object UserModel {
  
  val db = DatabaseConnection.db

  val users = TableQuery[UsersTable]

  def create( list: (String, String, String)  ) = {
    db.run( users.map( c => (c.username, c.email, c.digest_password) ) += list )
  }
}