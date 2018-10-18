package router

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import io.finch._
import io.circe.generic.auto._
import io.finch.circe._

trait RestInterface extends Resources {

  lazy val vendorService = new VendorResources()

  val endpoints: Service[Request, Response] = {
    vendorService.vendorEndpoints.toServiceAs[Application.Json]
  }

}

trait Resources extends VendorResources