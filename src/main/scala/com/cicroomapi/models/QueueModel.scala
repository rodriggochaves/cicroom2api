package com.cicroomapi.models

import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import com.cicroomapi.models.tables.QueueTable

// my imports
import com.cicroomapi.models.tables.QueueInsert

case class QueueParams(id: Option[Int], roomId : Int, username: String, time_stamp: String) {
    def toSave = ( roomId, username, time_stamp )
}

object QueueModel {
  val db = DatabaseConnection.db

  val queue = TableQuery[QueueTable]

  def create( params: QueueParams  ): Future[Int] = {
    val q = queue.map( c => ( c.roomId, c.username, c.time_stamp ) ) += params.toSave
    q.statements.foreach(println)
    db.run( q )
  }
}