package router

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import io.finch._
import io.circe.generic.auto._
import io.finch.circe._

trait RestInterface extends Resource {

  val brandsResources = new BrandsResources
  val brandTypeResource = new BrandTypeResources
  val userAccountResources = new UserAccountResources

  val endpoints: Service[Request, Response] = {
    val joinEndpoints = vendorEndpoints :+: brandTypeResource.brandTypeEndpoint :+: userAccountResources.userAccountEndpoints :+: brandsResources.brandTypeEndpoint
    joinEndpoints.toServiceAs[Application.Json]
  }

}

trait Resource extends VendorResources