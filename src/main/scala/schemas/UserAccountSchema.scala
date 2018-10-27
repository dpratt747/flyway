package schemas

import java.sql.Timestamp
import slick.jdbc.MySQLProfile.api._


case class UserAccount(userID: Option[Int], userName: String, email: String, password: String, insertionDate: Option[Timestamp], lastAccessedDate: Option[Timestamp])

class UserAccounts(tag: Tag) extends Table[UserAccount](tag, "user_account") {

  def userID = column[Int]("UserID", O.PrimaryKey, O.AutoInc)
  def userName = column[String]("UserName", O.SqlType("VARCHAR(100)"))
  def email = column[String]("Email", O.SqlType("VARCHAR(100)"))
  def password = column[String]("Password", O.SqlType("VARCHAR(100)"))
  def insertionDate = column[Timestamp]("InsertionDate", O.SqlType("timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP"))
  def lastAccessedDate = column[Timestamp]("LastAccessedDate", O.SqlType("timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP"))

  def * = (userID.?, userName, email, password, insertionDate.?, lastAccessedDate.?) <> (UserAccount.tupled, UserAccount.unapply)
}

