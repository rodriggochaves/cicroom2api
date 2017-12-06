package com.cicroomapi.models

import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import com.cicroomapi.models.tables.Room
import com.cicroomapi.models.tables.RoomsTable
import com.cicroomapi.models.tables.TableSchema
import slick.dbio.DBIOAction._
// my imports
import com.cicroomapi.models.tables.Room

case class RoomParams(id: Option[Int], description: Option[String], openningTime: Option[String], finalTime: Option[String], password: Option[String], queueSize: Option[Int]) {
  def toSave = ( description, openningTime, finalTime )
  def getDescription = (description)
  def toSaveModel = Room( null, description, openningTime, finalTime )
}

object RoomModel {
  val db = DatabaseConnection.db
  val rooms = TableQuery[RoomsTable]

  def create( params: RoomParams): Future[Int] = {
  	// val query = rooms returning rooms.map( r => (r.id.?, r.description.?, r.openningTime.?, r.finalTime.?) ) += params.toSaveModel
    val query = (rooms returning rooms.map(_.id)) += params.toSaveModel
  	query.statements.foreach(println)
  	db.run(query)
  }

  def list() = {
  	// val query = (rooms.map(r => (r.id.?, r.description.? )).result).length
 	  val q2 = sql"""
      select rooms.id, rooms.description, count(queue.id) 
      from rooms left 
      join queue on rooms.id = queue.room_id 
      group by rooms.id, rooms.description;"""
    .as[(Option[Int], Option[String], Option[Int])]
  	// query.statements.foreach(println)
  	q2.statements.foreach(println)
  	db.run(q2)    
  }

  def listUsers(params:Int) = {
    val query = TableSchema.queue.filter(_.roomId === params).result
    db.run(query)
  }

  def delete(params: Int) = {
    val action = TableSchema.queue.filter(_.roomId === params).delete
    db.run(action)
    val query = rooms.filter(_.id === params).delete
    db.run(query)
  }

}