package com.cicroomapi.models

import org.scalatra._
import slick.driver.PostgresDriver.api._

class RolesTable(tag: Tag) extends Table[(Int, String)](tag, "roles") {
  
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def description = column[String]("description",O.Unique)

  def * = (id, description)

}