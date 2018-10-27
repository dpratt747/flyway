package router

import java.sql.Timestamp

import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}
import io.finch._
import io.finch.syntax._
import schemas.{UserAccount, Vendor}
import shapeless.{:+:, CNil}
import io.finch.circe._
import router.handlers.{UserAccountHandler, VendorHandler}
import router.twitter_custom.TwitterConversion
import finch_custom.DecodeEncode._
import router.finch_custom.DecodeEncodeCustomFormat

import scala.concurrent.ExecutionContext.Implicits.global
import runner.LogTrait

class UserAccountResources extends TwitterConversion with LogTrait with DecodeEncodeCustomFormat {

  /**
    * endpoint to add new user account
    *  example json body {"userID": null, "userName": "david_username", "email": "david_email@domain.com", "password": "password" }
    */
  private val addUserAccount: Endpoint[UserAccount] = post("addnewuser" :: jsonBody[UserAccount]) { user: UserAccount =>
    log.info(s"Request made to /addnewuser with the following body: $user")
    UserAccountHandler.addUser(user).asTwitter.map(Ok)
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /addnewuser: $e")
      InternalServerError(e)
  }

  /**
    * endpoint to return list of all vendor accounts
    */
  private val getUserAccount: Endpoint[Seq[UserAccount]] = get("getusers") {
    log.info("Request made to /getusers")
    UserAccountHandler.getUserAccount.asTwitter.map{ x =>
      log.info("Request to /getusers succeeded")
      Ok(x)
    }
  }

  }


  val vendorEndpoints: Endpoint[Vendor :+: String :+: Seq[Vendor] :+: CNil] = addUserAccount :+: getUser :+: getVendors
}


