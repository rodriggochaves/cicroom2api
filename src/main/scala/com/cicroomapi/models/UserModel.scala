package com.cicroomapi.models

import slick.driver.PostgresDriver.api._
import slick.dbio.DBIOAction._


object UserModel {
  
  val db = DatabaseConnection.db

  val users = TableQuery[UsersTable]

  def create( list: (String, String, String,Int)  ) = {
    db.run( users.map( c => (c.username, c.email, c.digest_password, c.roleId) ) += list )
  }

  // def select(list: (String) ) = {
  // 	db.run( users.filter(_.email === list))
  // }

}