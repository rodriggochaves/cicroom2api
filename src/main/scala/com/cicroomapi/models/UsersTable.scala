package com.cicroomapi.models

import org.scalatra._
import slick.driver.PostgresDriver.api._

class UsersTable(tag: Tag) extends Table[(Int, String, String, String,Int)](tag, "users") {
  
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def username = column[String]("username",O.Unique)
  def email = column[String]("email",O.Unique)
  def digest_password = column[String]("digest_password")
  def roleId = column[Int]("role_id")
  def role = foreignKey("SUP_FK",roleId,TableSchema.roles)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
  def * = (id, username, email, digest_password, roleId)


}