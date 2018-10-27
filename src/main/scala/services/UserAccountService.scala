package services

import runner.{userAccountTable, vendorTable}
import schemas.UserAccount
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

class UserAccountService {

  def insertUserAccount(userAccount: UserAccount): Future[UserAccount] = {
    val query =  userAccountTable.returning(userAccountTable.map(_.userID)) into ((a, b) =>
      a.copy(userID = Some(b))) += userAccount
    db.run(query)
  }

  def getUserAccountByName(userName: String)

  val db = Database.forConfig("mariadb")
}
