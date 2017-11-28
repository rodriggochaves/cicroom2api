package com.cicroomapi.models

import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import com.cicroomapi.models.tables.QueueTable
import java.sql.Timestamp

// my imports
import com.cicroomapi.models.tables.Queue

case class QueueParams(id: Option[Int], roomId : Int, username: String, timestamp: Option[Timestamp]) {
    def toSave = ( roomId, username )
}

object QueueModel {
  val db = DatabaseConnection.db

  val queue = TableQuery[QueueTable]

  def create( params: QueueParams  ): Future[Int] = {
    val q = queue.map( c => ( c.roomId, c.username ) ) += params.toSave
    q.statements.foreach(println)
    db.run( q )
  }
}