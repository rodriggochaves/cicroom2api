package com.cicroomapi.models

import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import com.cicroomapi.models.tables.RoomsTable

// my imports
import com.cicroomapi.models.tables.Room

case class RoomParams(id: Option[Int], description: Option[String], openningTime: Option[String], finalTime: Option[String], password: Option[String]) {
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
  	val query = rooms.map(r => (r.id.?, r.description.? )).result
  	query.statements.foreach(println)
  	db.run(query)    
  }

}