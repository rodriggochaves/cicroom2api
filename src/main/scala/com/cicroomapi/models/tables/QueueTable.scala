package com.cicroomapi.models.tables

import org.scalatra._
import slick.driver.PostgresDriver.api._
import slick.lifted.ProvenShape

case class QueueInsert(id: Option[Int], roomId: Int, username: String, time_stamp: String )

class QueueTable(tag: Tag) extends Table[QueueInsert](tag, "queue") {
  
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def roomId = column[Int]("room_id")
  def room = foreignKey("SUP_FK",roomId,TableSchema.queue)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
  def username = column[String]("username")
  def time_stamp = column[String]("time_stamp")
  def * = ( id.?, roomId, username, time_stamp ) <> ( QueueInsert.tupled, QueueInsert.unapply )

}