package router

import java.sql.Timestamp

import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}
import io.finch._
import io.finch.syntax._
import schemas.Vendor
import shapeless.{:+:, CNil}
import io.finch.circe._
import router.handlers.VendorHandler
import router.twitter_custom.TwitterConversion
import finch_custom.DecodeEncode._
import router.finch_custom.DecodeEncodeCustomFormat

import scala.concurrent.ExecutionContext.Implicits.global
import runner.LogTrait

class VendorResources extends TwitterConversion with LogTrait with DecodeEncodeCustomFormat {

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
    VendorHandler.addVendor(vendor).asTwitter.map(Ok)
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /addvendor: $e")
      InternalServerError(e)
  }

  /**
    * endpoint to return list of all vendor accounts
    */
  private val getVendors: Endpoint[Seq[Vendor]] = get("getvendors") {
    log.info("Request maDe to /getvendors")
    VendorHandler.getVendors.asTwitter.map(Ok)
  }


  val vendorEndpoints: Endpoint[Vendor :+: String :+: Seq[Vendor] :+: CNil] = addVendor :+: ping :+: getVendors
}


