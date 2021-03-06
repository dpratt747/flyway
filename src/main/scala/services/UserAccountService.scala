package services

import runner.{userAccountTable, vendorTable}
import schemas.UserAccount
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global


import scala.concurrent.Future

class UserAccountService {

  def deleteUserAccountByName(userName: String): Future[Option[String]] = {
    val query = userAccountTable.filter(_.userName === userName)
    val action = for {
      results <- query.map(_.userName).take(1).result.headOption
      _ <- query.delete
    } yield results
    db.run(action)
  }

  def deleteUserAccountById(id: Int): Future[Option[String]] = {
    val query = userAccountTable.filter(_.userID === id)
    val action = for {
      results <- query.map(_.userName).take(1).result.headOption
      _ <- query.delete
    } yield results
    db.run(action)
  }

  def insertUserAccount(userAccount: UserAccount): Future[UserAccount] = {
    val query =  userAccountTable.returning(userAccountTable.map(_.userID)) into ((a, b) =>
      a.copy(userID = Some(b))) += userAccount
    db.run(query)
  }

  def updateUserAccount(userAccount: UserAccount): Future[Int] = {
    val query = userAccountTable.insertOrUpdate(userAccount)
    db.run(query)
  }

  def getUserAccountById(id: Int): Future[Option[UserAccount]] = {
    val query = userAccountTable.filter(_.userID === id).take(1).result.headOption
    db.run(query)
  }

  def getUserAccountByEmail(email: String): Future[Option[UserAccount]] = {
    val query = userAccountTable.filter(_.email === email).take(1).result.headOption
    db.run(query)
  }

  def getUserAccountByName(userName: String): Future[Option[UserAccount]] = {
    val query = userAccountTable.filter(_.userName === userName).take(1).result.headOption
    db.run(query)
  }

  def getUserAccounts: Future[Seq[UserAccount]] = {
    val query = userAccountTable.result
    db.run(query)
  }

  private val db = ConnectionFactory("mariadb").loadMysql
}
