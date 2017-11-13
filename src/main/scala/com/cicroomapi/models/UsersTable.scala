package com.cicroomapi.models

import org.scalatra._
import slick.driver.PostgresDriver.api._

class UsersTable(tag: Tag) extends Table[(Int, String, String, String)](tag, "users") {
  
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def username = column[String]("username")
  def email = column[String]("email")
  def digest_password = column[String]("digest_password")

  def * = (id, username, email, digest_password)

}