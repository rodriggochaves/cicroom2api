package com.cicroomapi.models

import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import slick.dbio.Effect.Read
import com.cicroomapi.models.tables.QueueTable
import java.sql.Timestamp

// my imports
import com.cicroomapi.models.tables.Queue

case class QueueParams(id: Option[Int], 
                       roomId : Int, 
                       username: String, 
                       timestamp: Timestamp = new Timestamp(new java.util.Date().getTime()), 
                       queueSize:Option[Int]) {
    def toSave = ( roomId, username, timestamp )
    def toSaveModel = Queue( Some(-1), roomId, username, Some(timestamp) )
}

object QueueModel {
  val db = DatabaseConnection.db

  val queue = TableQuery[QueueTable]

  def create( params: QueueParams  ) = {
    val q = (queue returning queue) += params.toSaveModel
    db.run(q)
  }

  def find( params: QueueParams ) = {
    // val res = sql""" select queue.room_id,count(queue.username) from queue where queue.room_id = ${params.roomId};""".as[(Option[Int],Option[Int])]
    val query = queue.filter(_.roomId === params.roomId).result 
    // res.statements.foreach(println)
    // println(res)
    db.run( query )
  }

  def delete(params: Int) = {
    val q = queue.filter(_.id === params)
    val action = q.delete
    db.run(action)
  }

  def findByRoom( roomId: Int ): Future[Seq[Queue]] = {
    val q = queue.filter(_.roomId === roomId).result
    db.run(q)
  }
}