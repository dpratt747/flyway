package services

import runner.InvalidDbCall
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._


trait ConnectionFactory {
  def loadMysql: MySQLProfile.backend.Database
}

object ConnectionFactory {
  private class MariaDB extends ConnectionFactory {
    override def loadMysql: MySQLProfile.backend.DatabaseDef = {
     Database.forConfig("mariadb")
    }
  }

  def apply(db: String): ConnectionFactory = {
    db match {
      case "mariadb" => new MariaDB
      case _ => throw InvalidDbCall("Attempting to create a connection to an unexpected db")
    }
  }

}
