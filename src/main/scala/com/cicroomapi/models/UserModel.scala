package com.cicroomapi.models

import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import com.cicroomapi.models.tables.UsersTable
import com.cicroomapi.models.Cypher

// my imports
import com.cicroomapi.models.tables.User

case class UserParams(id: Option[Int], username: Option[String], email: String, password: String, roleId: Int) {
  def toSave = ( username, email, Cypher.encipher("shh" , password),roleId )
}

object UserModel {
  
  val db = DatabaseConnection.db

  val users = TableQuery[UsersTable]

  def create( params: UserParams  ): Future[Int] = {
    db.run( users.map( c => (c.username.?, c.email, c.digest_password, c.roleId) ) += params.toSave )
  }
  def find( email: String ): Future[Option[User]] = {
    val res = users.filter( _.email === email ).result.headOption
    res.statements.foreach(println)
    db.run( res )
  }

}