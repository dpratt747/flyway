package router

import akka.http.scaladsl.server.Route
import dao.VendorService
import scala.concurrent.ExecutionContext

trait RestInterface extends Resources {

  implicit def executionContext: ExecutionContext
  lazy val vendorService = new VendorService()

  val routes: Route = vendorRoutes

}

trait Resources extends VendorResources