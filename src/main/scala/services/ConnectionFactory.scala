package services

import runner.{InvalidDbCall, LogTrait}
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._


trait ConnectionFactory {
  def loadMysql: MySQLProfile.backend.Database
}

object ConnectionFactory extends LogTrait{
  private class MariaDB extends ConnectionFactory {
    override def loadMysql: MySQLProfile.backend.DatabaseDef = {
      val title = "mariadb"
      log.info(s"loading config settings for $title from application.conf")
      Database.forConfig(title)
    }
  }

  def apply(db: String): ConnectionFactory = {
    db match {
      case "mariadb" => new MariaDB
      case _ => throw InvalidDbCall("Attempting to create a connection to an unexpected db")
    }
  }

}
