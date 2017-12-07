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

  implicit val system = ActorSystem()
  implicit val db = DatabaseConnection.db

  override def init( context: ServletContext ) {
    context.mount(new ApplicationController(), "/api/application/")
    context.mount(new RoomsController(), "/api/rooms/")
    context.mount(new QueueController(), "/api/queues/")
  }

  override def destroy(context: ServletContext) {
    system.shutdown()
    super.destroy(context)
    DatabaseConnection.destroy
  }

}
