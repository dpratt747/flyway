package router.handlers

import java.sql.Timestamp
import java.util.Calendar

import schemas.UserAccount
import services.UserAccountService

import scala.concurrent.Future

object UserAccountHandler {

  private val currentDate = new Timestamp(Calendar.getInstance.getTime.getTime)

  def login(inputUser: UserAccount): Future[Option[UserAccount]] = {
    val action = for {
      user <- getUser(inputUser.userName).collect{case Some(item) => item}
      passwordCheck = user.password.equals(inputUser.password)
    } yield (user, passwordCheck)
    action.map{ case (user, bool) =>
      if (bool){
        userAccountService.updateUserAccount(user.copy(lastAccessedDate = Some(currentDate)))
        Some(user)
      } else {
        None
      }
    }
  }


  private val userAccountService = new UserAccountService

  def addUser(user: UserAccount) : Future[UserAccount] = {
    val userWithDates = user.copy(insertionDate = Some(currentDate), lastAccessedDate = Some(currentDate))
    userAccountService.insertUserAccount(userWithDates)
  }

  def getAllUserAccounts: Future[Seq[UserAccount]] = {
    userAccountService.getUserAccounts
  }

  def getUser[A](x: A): Future[Option[UserAccount]] = {
    x match {
      case username: String => userAccountService.getUserAccountByName(username)
      case id: Int => userAccountService.getUserAccountById(id)
      case _ => Future.successful(None)
    }
  }


}
