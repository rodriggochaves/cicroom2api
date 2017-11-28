package com.cicroomapi.controllers

import com.cicroomapi.models.tables.User 
import com.cicroomapi.models.tables.Room 

case class Response(status: String, user: Option[User] = null)
case class ResponseRoom(status: String, rooms: Vector[(Option[String],Option[Int])] = null )

case class ResponseQueue(status: String, room: Vector[(Option[Int],Option[String])] = null)
