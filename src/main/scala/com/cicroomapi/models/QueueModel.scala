package com.cicroomapi.models

import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import com.cicroomapi.models.tables.QueueTable
import java.sql.Timestamp

// my imports
import com.cicroomapi.models.tables.Queue

case class QueueParams(id: Option[Int], roomId : Int, username: String, timestamp: Timestamp = new Timestamp(new java.util.Date().getTime())) {
    def toSave = ( roomId, username, timestamp )
}

object QueueModel {
  val db = DatabaseConnection.db

  val queue = TableQuery[QueueTable]

  def create( params: QueueParams  ): Future[Int] = {
    val q = queue.map( c => ( c.roomId, c.username,c.timestamp ) ) += params.toSave
    q.statements.foreach(println)
    db.run( q )
  }
}