package router.handlers

import java.sql.Timestamp
import java.util.Calendar

import runner.{FailedToFindUser, ItemFailedToDelete, LogTrait}
import schemas.UserAccount
import services.UserAccountService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object UserAccountHandler extends LogTrait{

  type Username = String

  private val currentDate = new Timestamp(Calendar.getInstance.getTime.getTime)
  private val userAccountService = new UserAccountService

  def login(inputUser: UserAccount): Future[Option[UserAccount]] = {
    val action = for {
      user <- getUser(inputUser.userName).collect{ case Some(item) => item }
      passwordCheck = Hash.isPasswordEqualToHash(inputUser.password, user.password)
    } yield (user, passwordCheck)

    action.map{ case (user, bool) =>
      if (bool){
        val userToReturn = user.copy(lastAccessedDate = Some(currentDate))
        userAccountService.updateUserAccount(userToReturn)
        Some(userToReturn)
      } else {
        None
      }
    }
  }

  def addUser(user: UserAccount) : Future[UserAccount] = {
    val userWithDates = user.copy(insertionDate = Some(currentDate), lastAccessedDate = Some(currentDate), password = Hash.encryptPassword(user.password))
    userAccountService.insertUserAccount(userWithDates)
  }

  def getAllUserAccounts: Future[Seq[UserAccount]] = {
    userAccountService.getUserAccounts
  }

  def getUser[A](input: A): Future[Option[UserAccount]] = {
    input match {
      case username: String => userAccountService.getUserAccountByName(username)
      case id: Int => userAccountService.getUserAccountById(id)
      case _ => Future.failed(throw FailedToFindUser(s"Failed to find user, invalid method call: $input"))
    }
  }

  def removeUsers[A](seq: Iterable[A]): Future[Iterable[Username]] = {
    val action = Future.sequence(seq.map(removeUser(_)))
    val successfulDeletions = action.map(_.collect{ case Some(username) => username })
    successfulDeletions.map{input => input.toList.intersect(seq.toList)}
  }

  def removeUser[A](input: A): Future[Option[Username]] = {
    input match {
      case id: Int => userAccountService.deleteUserAccountById(id)
      case userName: String => userAccountService.deleteUserAccountByName(userName)
      case _ => Future.failed(throw ItemFailedToDelete(s"Failed to delete user, invalid method call: $input"))
    }
  }

}
