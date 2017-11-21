package com.cicroomapi.controllers

import com.cicroomapi.models.tables.User 

case class Response(status: String, user: Option[User])
