package com.cicroomapi.models

import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import com.cicroomapi.models.tables.RoomsTable
import com.cicroomapi.models.tables.TableSchema
import slick.dbio.DBIOAction._
// my imports
import com.cicroomapi.models.tables.Room

case class RoomParams(id: Option[Int], description: Option[String], openningTime: Option[String], finalTime: Option[String], password: Option[String], queueSize: Option[Int]) {
  def toSave = ( description, openningTime, finalTime )
  def getDescription = (description)
}

object RoomModel {
  val db = DatabaseConnection.db
  val rooms = TableQuery[RoomsTable]

  def create( params: RoomParams): Future[Int] = {
  	val query = rooms.map( r => (r.description.?, r.openningTime.?, r.finalTime.?) ) += params.toSave
  	query.statements.foreach(println)
  	db.run(query)  
  }

  def list() = {
  	// val query = (rooms.map(r => (r.id.?, r.description.? )).result).length
 	  val q2 = sql""" select description,count(*) from rooms join queue on rooms.id = queue.room_id group by description;""".as[(Option[String],Option[Int])]
  	// query.statements.foreach(println)
  	q2.statements.foreach(println)
  	db.run(q2)    
  }

  def delete(params: Int) = {
    val action = TableSchema.queue.filter(_.roomId === params).delete
    db.run(action)
    val query = rooms.filter(_.id === params).delete
    db.run(query)
  }

}