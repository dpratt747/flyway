package runner

import schemas.{BrandContactDetails, Vendor}

import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import dao.VendorDAO._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import router.RestInterface

import scala.io.StdIn


object ExecutionRunner extends App with RestInterface {
//  val a = BrandContactDetails(None, "07873327004" , "Fake Address Should add some addres authentication in the future maybe" )
//  val b = Vendor(None,"dpratt747", "dpratt747@gmail.com", "password")
//  Await.result( insertVendor(b).map(println(_)), 10 seconds)

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  private val config = ConfigFactory.load()
  private val host = config.getString("http.host")
  private val port = config.getInt("http.port")
  private val api = routes
//
//  private val route: Route = path("hello"){
//    get{
//      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Running api service</h1>"))
//    }
//  }

  private val bindingFuture = Http().bindAndHandle(api, host, port)

  println(s"Server online at http://localhost:5000/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done


}
