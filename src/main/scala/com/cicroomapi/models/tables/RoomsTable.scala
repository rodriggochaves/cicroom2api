package com.cicroomapi.models.tables

import org.scalatra._
import slick.driver.PostgresDriver.api._
import slick.lifted.ProvenShape

case class Room(id: Int, description: String, openningTime: String, finalTime: String)

class RoomsTable(tag: Tag) extends Table[Room](tag, "rooms") {
  
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def description = column[String]("description",O.Unique)
  def openningTime = column[String]("openningTime")
  def finalTime = column[String]("finalTime")

  def * = ( id, description, openningTime, finalTime ) <> ( Room.tupled, Room.unapply )
}