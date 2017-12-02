package com.cicroomapi.models

import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import com.cicroomapi.models.tables.QueueTable
import java.sql.Timestamp

// my imports
import com.cicroomapi.models.tables.Queue

case class QueueParams(id: Option[Int], roomId : Int, username: String, timestamp: Timestamp = new Timestamp(new java.util.Date().getTime()), queueSize:Option[Int]) {
    def toSave = ( roomId, username, timestamp )
}

object QueueModel {
  val db = DatabaseConnection.db

  val queue = TableQuery[QueueTable]

  def create( params: QueueParams  ) = {
    val q = queue.map( c => ( c.roomId, c.username,c.timestamp ) ) += params.toSave
    q.statements.foreach(println)
    // println(params)
    db.run( q )
    this.find(params)
  }

  def find( params: QueueParams ) = {
    val res = sql""" select queue.room_id,queue.username from queue where queue.room_id = ${params.roomId};""".as[(Option[Int],Option[String])]
    res.statements.foreach(println)
    println(res)
    db.run( res )
  }

  def delete(params: Int) = {
    val q = queue.filter(_.id === params)
    val action = q.delete
    db.run(action)
  }
}