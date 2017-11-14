import com.mchange.v2.c3p0.ComboPooledDataSource
import org.slf4j.LoggerFactory
import org.scalatra._
import javax.servlet.ServletContext
import slick.jdbc.JdbcBackend.Database
import org.scalatra.CorsSupport._

import com.cicroomapi.controllers._
import com.cicroomapi.models._

class ScalatraBootstrap extends LifeCycle {
  
  override def init( context: ServletContext ) {
    val db = DatabaseConnection.db

    context.initParameters(AllowedOriginsKey) = "*"
    context.initParameters(AllowedMethodsKey) = "*"
    context.initParameters(AllowedHeadersKey) = "*"
    context.mount(new ApplicationController(db), "/api/application/")
    context.mount(new UsersController(db), "/api/users/")
    context.mount(new RoomsController(db), "/api/rooms/")
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
    DatabaseConnection.destroy
  }

}
