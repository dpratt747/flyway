package crud

import java.io.{File, FileNotFoundException}

import org.slf4j.{Logger, LoggerFactory}
import com.typesafe.config.{ConfigFactory, ConfigObject}

import scala.util.{Failure, Success, Try}

object SchemaGen extends App {

  private val file: File = new File("src\\main\\resources\\application.conf")
  private val logger = LoggerFactory.getLogger(c)

  private val config = Try(ConfigFactory.parseFile(file))
  val dbConfig = config match {
    case Success(s) => s.getObject("mariadb")
    case Failure(e) => logger.error("Config parse failed:", e)
  }


  val dbConf: ConfigObject = config.get.getObject("mariadb")

}
