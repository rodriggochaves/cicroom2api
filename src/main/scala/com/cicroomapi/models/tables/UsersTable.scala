package com.cicroomapi.models.tables

import org.scalatra._
import slick.driver.PostgresDriver.api._
import slick.lifted.ProvenShape

case class User(id: Option[Int], username: Option[String], email: Option[String], digest_password: Option[String], role_id:Int)

class UsersTable(tag: Tag) extends Table[User](tag, "users") {
  
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def username = column[String]("username")
  def email = column[String]("email")
  def digest_password = column[String]("digest_password")

  def * = ( id.?, username.?, email.?, digest_password.? ) <> ( User.tupled, User.unapply )

}