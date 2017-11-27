import com.mchange.v2.c3p0.ComboPooledDataSource
import org.slf4j.LoggerFactory
import org.scalatra._
import javax.servlet.ServletContext
import slick.jdbc.JdbcBackend.Database
import org.scalatra.CorsSupport._
import _root_.akka.actor.{Props, ActorSystem} 

import com.cicroomapi.controllers._
import com.cicroomapi.models._

class ScalatraBootstrap extends LifeCycle {

  val system = ActorSystem()
  // val myActor = system.actorOf(Props[MyActor])
  
  override def init( context: ServletContext ) {
    val db = DatabaseConnection.db

    context.initParameters(AllowedOriginsKey) = "*"
    context.initParameters(AllowedMethodsKey) = "*"
    context.initParameters(AllowedHeadersKey) = "*"
    context.mount(new ApplicationController(db), "/api/application/")
    context.mount(new UsersController(db, system), "/api/users/")
    context.mount(new RoomsController(db), "/api/rooms/")
    context.mount(new QueueController(db,system), "/api/queue/")
    context.mount(new AuthController(system), "/api/auth/")
  }

  override def destroy(context: ServletContext) {
    system.shutdown()
    super.destroy(context)
    DatabaseConnection.destroy
  }

}
