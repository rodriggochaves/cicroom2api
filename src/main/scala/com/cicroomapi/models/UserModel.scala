package com.cicroomapi.models

import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import com.cicroomapi.models.tables.UsersTable
import com.cicroomapi.models.Cypher

// my imports
import com.cicroomapi.models.tables.User

case class UserParams(id: Option[String], username: Option[String], var email: String, password: String, role_id: Int) {
  def toSave = ( username, email, Cypher.encipher("shh" , password),role_id )
}

object UserModel {
  
  val db = DatabaseConnection.db

  val users = TableQuery[UsersTable]

  def create( params: UserParams  ): Future[Int] = {
    db.run( users.map( c => (c.username.?, c.email, c.digest_password, c.role_id) ) += params.toSave )
  }
_id
  def find( email: String ): Future[Option[User]] = {
    val res = users.filter( _.email === email ).result.headOption
    res.statements.foreach(println)
    db.run( res )
  }

}