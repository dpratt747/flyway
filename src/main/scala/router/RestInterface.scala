package router

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import io.finch._
import io.circe.generic.auto._
import io.finch.circe._

trait RestInterface extends Resources {

  lazy val vendorResource = new VendorResources
  lazy val brandTypeResource = new BrandTypeResources
  lazy val userAccountResources = new UserAccountResources

  val endpoints: Service[Request, Response] = {
    val joinEndpoints = vendorResource.vendorEndpoints :+: brandTypeResource.brandTypeEndpoint :+: userAccountResources.userAccountEndpoints
    joinEndpoints.toServiceAs[Application.Json]
  }

}

trait Resources extends VendorResources