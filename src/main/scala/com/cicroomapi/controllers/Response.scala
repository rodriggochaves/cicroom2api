package com.cicroomapi.controllers

import com.cicroomapi.models.tables.User 
import com.cicroomapi.models.tables.Room 
import com.cicroomapi.models.tables.Queue

case class Response(status: String, user: Option[User] = null)

case class ErrorResponse(status: String = "Error", desc: Any = null)

case class ViewRoom(id: Option[Int], description: Option[String], relativeQueue: Option[Int])

case class ResponseCreatedRoom(room: Room)

case class QueuePositionResponse( position: Int )

case class ResponseRoom(status: String, rooms: Vector[ViewRoom])

case class ResponseQueue(status: String, queueId: Option[Int], roomId: Int, relativeQueue: Int )

case class ResponseRoomUsers(status: String, users: Seq[Queue] = null)