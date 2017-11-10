package com.github.rodriggochaves.api

import org.scalatra._
import slick.driver.PostgresDriver.api._

object Tables {
  class Rooms(tag: Tag) extends Table[(Int, String)](tag, "rooms") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def * = (id, name)
  }

  class Users(tag: Tag) extends Table[(Int, String, String, String)](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")
    def email = column[String]("email")
    def digest_password = column[String]("digest_password")
    def * = (id, username, email, digest_password)
  }

  val rooms = TableQuery[Rooms]
  val users = TableQuery[Users]

  val schema = rooms.schema ++ users.schema

  val dropSchemaAction = schema.drop
  val createSchemaAction = schema.create

  def createUser = users.map( c => (c.username, c.email, c.digest_password) ) += ("rbonifacio", "rodrigo123@email.com", "password")
}

class Api(val db: Database) extends ScalatraServlet {

  get("/") {
    // views.html.hello()
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

  get("/db/drop") {
    db.run(Tables.dropSchemaAction)
  }

  get("/db/create") {
    db.run(Tables.createSchemaAction)
  }

}
