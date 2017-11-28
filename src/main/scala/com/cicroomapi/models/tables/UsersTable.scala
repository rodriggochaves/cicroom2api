package com.cicroomapi.models.tables

import org.scalatra._
import slick.driver.PostgresDriver.api._
import slick.lifted.ProvenShape

case class User(id: Option[Int], username: Option[String], email: Option[String], digest_password: Option[String], roleId: Option[Int])

class UsersTable(tag: Tag) extends Table[User](tag, "users") {
  
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def username = column[String]("username", O.Unique)
  def email = column[String]("email",O.Unique)
  def digest_password = column[String]("digest_password")
  def roleId = column[Int]("role_id")
  def role = foreignKey("ROLE_FK",roleId,TableSchema.roles)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
  def * = ( id.?, username.?, email.?, digest_password.?, roleId.? ) <> ( User.tupled, User.unapply )

}