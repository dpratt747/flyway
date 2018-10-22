package runner

import scala.util.{Failure, Success, Try}
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.http.filter.Cors
import com.twitter.finagle.param.Stats
import com.twitter.util.Await
import com.twitter.server.TwitterServer
import com.typesafe.config.ConfigFactory
import router.RestInterface


object ExecutionRunner extends RestInterface with TwitterServer with LogTrait {

  private val config = ConfigFactory.load()

  private val environment: String = Try {
    config.getString("env")
  } match {
    case Success(value) =>
      log.info(s"Successfully retrieved environment variable from config: $value ")
      value
    case Failure(e) =>
      log.error(s"Failed to retrieve environment variable from config. Setting to variable to dev $e")
      "dev"
  }

  private val policy: Cors.Policy = {
    if (environment.equals("dev")) {
      Cors.Policy(
        allowsOrigin = _ => Some("*"),
        allowsMethods = _ => Some(Seq("GET", "POST", "DELETE")),
        allowsHeaders = _ => Some(Seq("Accept", "Content-Type"))
      )
    } else {
      Cors.Policy(
        allowsOrigin = _ => Some("*"),
        allowsMethods = _ => Some(Seq("GET", "POST", "DELETE")),
        allowsHeaders = _ => Some(Seq("Accept", "Content-Type"))
      )
    }
  }

  private val api: Service[Request, Response] = {
    new Cors.HttpFilter(policy).andThen(endpoints)
  }

  def main(): Unit = {

    val server = Http.server
      .configured(Stats(statsReceiver))
      .serve(":9090", api)

    onExit {
      server.close()
    }

    Await.ready(adminHttpServer)

  }


}
