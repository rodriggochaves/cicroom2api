package com.cicroomapi.controllers

import com.cicroomapi.models.tables.User 
import com.cicroomapi.models.tables.Room 
import com.cicroomapi.models.tables.Queue

case class Response(status: String, user: Option[User] = null)
case class ResponseRoom(status: String, rooms: Vector[(Option[String],Option[Int])] = null )

case class ResponseQueue(status: String, roomId: Int, relative_queue: Int )
