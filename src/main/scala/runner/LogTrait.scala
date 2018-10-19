package runner

import com.twitter.logging.{ConsoleHandler, Logger}

trait LogTrait {
  val log: Logger = Logger.get(getClass.getName)
  private val handler = new ConsoleHandler()
  log.clearHandlers()
  log.addHandler(handler)
}
