package router.handlers

import schemas.UserAccount
import services.UserAccountService

import scala.concurrent.Future

object UserAccountHandler {

  private val userAccountService = new UserAccountService

  def addUser(user: UserAccount) : Future[UserAccount] = {
    userAccountService.insertUserAccount(user)
  }

}
