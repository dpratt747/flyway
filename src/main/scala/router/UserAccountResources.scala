package router

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import io.finch._
import io.finch.syntax._
import schemas.UserAccount
import shapeless.{:+:, CNil}
import io.finch.circe._
import router.handlers.UserAccountHandler
import router.twitter_custom.TwitterConversion
import finch_custom.DecodeEncode._
import router.finch_custom.DecodeEncodeCustomFormat

import scala.concurrent.ExecutionContext.Implicits.global
import runner.{LogTrait, UserAlreadyExists}

class UserAccountResources extends TwitterConversion with LogTrait with DecodeEncodeCustomFormat {

  type Username = String

  private val signupUserAccount: Endpoint[UserAccount] = post("signup" :: jsonBody[UserAccount]) { user: UserAccount =>
    log.info(s"Request made to /signup with the following body: $user")
    UserAccountHandler.addUser(user).asTwitter.map{ res =>
      log.info(s"Request to /signup successful: $res")
      Ok(res)
    }
  } handle {
    case e: MySQLIntegrityConstraintViolationException =>
      log.error(s"Error during request to /signup: $e")
      throw UserAlreadyExists(e.getMessage)
    case e: Exception =>
      log.error(s"Error during request to /signup: $e")
      InternalServerError(e)
  }

  private val getUserAccount: Endpoint[Option[UserAccount]] = get("getuser" :: param[Int]("userID")) { id: Int =>
    log.info(s"Request made to /getuser with the following param: $id")
    UserAccountHandler.getUser(id).asTwitter.map{ res =>
      log.info(s"Request to /getuser successful: $res")
      Ok(res)
    }
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /getuser: $e")
      InternalServerError(e)
  }

  private val removeUserAccount: Endpoint[Option[Username]] = put("removeuser" :: jsonBody[UserAccount]) { user: UserAccount =>
    log.info(s"Request made to /removeuser with the following body: $user")
    UserAccountHandler.removeUser(user).asTwitter.map{ res =>
      log.info(s"Request to /removeuser successful: $res")
      Ok(res)
    }
  } handle {
    case e: Exception =>
      log.info(s"Error during request to /removeuser: $e")
      InternalServerError(e)
  }

  private val getAllUserAccounts: Endpoint[Seq[UserAccount]] = get("getusers") {
    log.info("Request made to /getusers")
    UserAccountHandler.getAllUserAccounts.asTwitter.map { res =>
      log.info("Request to /getusers succeeded")
      Ok(res)
    }
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /getusers: $e")
      InternalServerError(e)
  }

  private val loginUserAccount = put("login" :: jsonBody[UserAccount]) { user: UserAccount =>
    log.info(s"Request made to /login with $user")
    UserAccountHandler.login(user).asTwitter.map { res =>
      log.info(s"Request made to /login succeeded: returning: $res")
      Ok(res)
    } handle {
      case e: Exception =>
        log.error(s"Error during request to /login: $e")
        InternalServerError(e)
    }
  }


  val userAccountEndpoints: Endpoint[UserAccount :+: Seq[UserAccount] :+: Option[UserAccount] :+: Option[UserAccount] :+: Option[Username] :+: CNil]
  = signupUserAccount :+: getAllUserAccounts :+: loginUserAccount :+: getUserAccount :+: removeUserAccount
}


