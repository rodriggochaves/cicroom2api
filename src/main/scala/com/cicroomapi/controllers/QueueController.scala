package com.cicroomapi.controllers

// lib import
import org.scalatra._
import org.scalatra.json._
import org.json4s.JsonAST._
import org.json4s.{DefaultFormats, Formats}
import slick.jdbc.JdbcBackend.Database
import org.scalatra.FutureSupport

// async libs
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success, Try}
import _root_.akka.actor.ActorSystem
import dispatch._

// my imports
import com.cicroomapi.models.QueueParams
import com.cicroomapi.models.QueueModel
import com.cicroomapi.models.tables.Queue
import com.cicroomapi.models.tables.TableSchema
import com.cicroomapi.models.tables.QueueTable
import com.cicroomapi.models.RoomModel
import slick.driver.PostgresDriver.api._
import slick.dbio.DBIOAction

class QueueController( implicit val db: Database, implicit val system: ActorSystem ) 
  extends AbstractController with JacksonJsonSupport with FutureSupport {

  protected implicit def executor: ExecutionContext = system.dispatcher
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  post("/") {
    val parameters = parsedBody.extract[Map[String, QueueParams]]
    val queueParams = parameters("queue")
    new AsyncResult{
      val is: Future[_] = {
        createQueue( queueParams )
      }
    }
  }

  delete("/:id") {
    new AsyncResult{
      val is: Future[_] = QueueModel.delete(params("id").toInt).fold(
        _ => NotFound(ErrorResponse("Error", "not found"), headers),
        _ => Ok(Response("ok"), headers)
      )
    }
  }

  private def findRoom( roomId: Int ) = {
    RoomModel.find( roomId )
  }

  private def createQueue( queueParams: QueueParams ) : Future[_] = {
    findRoom(queueParams.roomId).map( s =>
      s match {
        case Right(room) => QueueModel.create(queueParams).map(queue => findAllQueueByRoom(queue))
        case Left(err) => NotFound(ErrorResponse("Error", err))
      }
    )
  }

  private def findAllQueueByRoom( queue: Queue ) = {
    QueueModel.findByRoom(queue.roomId).map( queues =>
      Ok(ResponseQueue("ok", queue.id, queue.roomId, queues.length), headers)
    )
  }


}