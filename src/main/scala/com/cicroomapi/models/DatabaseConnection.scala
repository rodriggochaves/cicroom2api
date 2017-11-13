package  com.cicroomapi.models

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.slf4j.LoggerFactory
import slick.jdbc.JdbcBackend.Database

object DatabaseConnection {
  
  val logger = LoggerFactory.getLogger(getClass)

  val cpds = new ComboPooledDataSource
  logger.info("Created c3p0 connection pool")

  var db = Database.forDataSource(cpds, None)

  def destroy {
    closeDbConnection
  }

  private def closeDbConnection() {
    logger.info("Closing c3po connection pool")
    cpds.close
  }

}