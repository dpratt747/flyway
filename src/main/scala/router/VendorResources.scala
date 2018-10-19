package router

import io.finch._
import io.finch.syntax._
import schemas.Vendor
import services.VendorService
import shapeless.{:+:, CNil}
import io.finch.circe._
import router.DecodeEncode._
import router.handlers.VendorHandler

import scala.concurrent.ExecutionContext.Implicits.global
import runner.LogTrait

class VendorResources extends TwitterConversion with LogTrait {

  private val ping: Endpoint[String] = get("ping") {
    log.info("Request made to /ping")
    Ok("pong")
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /ping: $e")
      InternalServerError(e)
  }

  /**
    * endpoint to add new vendor account
    *  example json body {"userID": null, "userName": "david_username", "email": "david_email@domain.com", "password": "password" }
    */
  private val addVendor: Endpoint[Vendor] = post("addvendor" :: jsonBody[Vendor]) { vendor: Vendor =>
    log.info(s"Request made to /addvendor with the following body: $vendor")
//    VendorService.insertVendor(vendor).asTwitter.map(Ok)
    VendorHandler.addVendor(vendor).asTwitter.map(Ok)
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /addvendor: $e")
      InternalServerError(e)
  }

  val vendorEndpoints: Endpoint[Vendor :+: String :+: CNil] = addVendor :+: ping

}


