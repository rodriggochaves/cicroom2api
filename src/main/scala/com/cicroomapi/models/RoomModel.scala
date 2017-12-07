package com.cicroomapi.models

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import slick.driver.PostgresDriver.api._
import com.cicroomapi.models.tables.Room
import com.cicroomapi.models.tables.RoomsTable
import com.cicroomapi.models.tables.TableSchema
import slick.dbio.DBIOAction._

// my imports
import com.cicroomapi.models.tables.Room

case class RoomParams(id: Option[Int], description: Option[String], openningTime: Option[String], finalTime: Option[String], password: Option[String], queueSize: Option[Int]) {
  def toSave = ( description, openningTime, finalTime )
  def toSaveModel(): Option[Room] = {
    ( description, openningTime, finalTime ) match {
      case ( Some(d), Some(o), Some(f) ) => Some(Room( 0, d, o, f ));
      case _ => None
    }
  }
}

object RoomModel {
  val db = DatabaseConnection.db
  val rooms = TableQuery[RoomsTable]

  def create( params: RoomParams): Either[String, Future[Room]] = {
  	// val query = rooms returning rooms.map( r => (r.id.?, r.description.?, r.openningTime.?, r.finalTime.?) ) += params.toSaveModel
    params.toSaveModel match {
      case Some(room) => {
        val query = (rooms returning rooms) += room
        query.statements.foreach(println)
        Right(db.run(query))
      }
      case None => Left("error")
    }
  	
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
    val query = TableSchema.queue.filter(_.roomId === params).sortBy(_.timestamp).result
    db.run(query)
  }

  def delete(params: Int) = {
    val action = TableSchema.queue.filter(_.roomId === params).delete
    db.run(action)
    val query = rooms.filter(_.id === params).delete
    db.run(query)
  }

}