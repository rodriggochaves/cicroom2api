package com.cicroomapi.models

import slick.driver.PostgresDriver.api._

object RoleModel {
  
  val db = DatabaseConnection.db

  val roles = TableQuery[RolesTable]

  def create = {
    db.run( roles.map( c => (c.description) ) += "Aluno" )
    db.run( roles.map( c => (c.description) ) += "Coordenador" )
    db.run( roles.map( c => (c.description) ) += "Adminstrador" )
  }


}