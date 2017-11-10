import com.github.rodriggochaves.cicroomapi._
import com.mchange.v2.c3p0.ComboPooledDataSource
import org.slf4j.LoggerFactory
import org.scalatra._
import javax.servlet.ServletContext
import slick.jdbc.JdbcBackend.Database

class ScalatraBootstrap extends LifeCycle {
  
  val logger = LoggerFactory.getLogger(getClass)

  val cpds = new ComboPooledDataSource
  logger.info("Created c3p0 connection pool")

  override def init(context: ServletContext) {
    val db = Database.forDataSource(cpds, None)
    context.mount(new UsersController(db), "/api/users/")
    context.mount(new RoomsController(db), "/api/rooms/")
  }

  private def closeDbConnection() {
    logger.info("Closing c3po connection pool")
    cpds.close
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
    closeDbConnection
  }
}
