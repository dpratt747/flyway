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
  private val getAllUserAccounts: Endpoint[Seq[UserAccount]] = get("getusers") {
    log.info("Request made to /getusers")
    UserAccountHandler.getAllUserAccounts.asTwitter.map{ accounts =>
      log.info("Request to /getusers succeeded")
      Ok(accounts)
    }
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /getusers: $e")
      InternalServerError(e)
  }

  private val userLogin = put("login" :: jsonBody[UserAccount]) { user: UserAccount =>
    log.info(s"Request made to /login")
    UserAccountHandler.login(user).asTwitter.map{ userAccount =>
      log.info(s"Request made to /login succeeded")
      Ok(userAccount)
    } handle {
      case e: Exception =>
        log.error(s"Error during request to /login: $e")
        InternalServerError(e)
    }
  }

//  private val getUser: Endpoint[Option[UserAccount]] = get("getuser" :: jsonBody[Either[UserAccount, String]]) { userOrUsername: Either[UserAccount, String] =>
//    log.info(s"Request made to /getuser with the following body")
//    UserAccountHandler.getUser(userOrUsername).asTwitter.map(Ok)
//  } handle {
//    case e:Exception =>
//      log.error(s"Error during request to /getuser: $e")
//  }


  val userAccountEndpoints: Endpoint[UserAccount :+: Seq[UserAccount] :+: Option[UserAccount] :+: CNil] = addUserAccount :+:
    getAllUserAccounts :+: userLogin
}


