package com.cicroomapi.models.tables

import org.scalatra._
import slick.driver.PostgresDriver.api._

class RoomsTable(tag: Tag) extends Table[(Int, String)](tag, "rooms") {
  
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")

  def * = (id, name)
}