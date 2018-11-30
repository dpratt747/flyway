package runner

import com.twitter.logging.{ConsoleHandler, FileHandler, Logger}

trait LogTrait {
  val log: Logger = Logger.get(getClass.getName)
  private val cHandler = new ConsoleHandler
  log.clearHandlers()
  log.addHandler(cHandler)
  log.setLevel(java.util.logging.Level.FINE)
}
