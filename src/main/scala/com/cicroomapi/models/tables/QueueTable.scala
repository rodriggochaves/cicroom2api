package com.cicroomapi.models.tables

import org.scalatra._
import slick.driver.PostgresDriver.api._
import slick.lifted.ProvenShape

case class Queue(id: Option[Int], roomId: Int, username: String, timestamp: String )

class QueueTable(tag: Tag) extends Table[Queue](tag, "queue") {
  
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def roomId = column[Int]("room_id")
  def room = foreignKey("ROOM_FK",roomId,TableSchema.rooms)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
  def username = column[String]("username")
  def timestamp = column[String]("timestamp")
  def * = ( id.?, roomId, username, timestamp ) <> ( Queue.tupled, Queue.unapply )

}