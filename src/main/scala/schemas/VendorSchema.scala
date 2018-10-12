package schemas

import slick.jdbc.MySQLProfile.api._

case class Vendor(userID: Option[Int], userName: String, email: String, password: String)

class Vendors(tag: Tag) extends Table[Vendor](tag, "vendor") {

  def userID = column[Int]("UserID", O.PrimaryKey, O.AutoInc)
  def userName = column[String]("UserName", O.SqlType("VARCHAR(100)"))
  def email = column[String]("Email", O.SqlType("VARCHAR(100)"))
  def password = column[String]("Password", O.SqlType("VARCHAR(100)"))

  def * = (userID.?, userName, email, password) <> (Vendor.tupled, Vendor.unapply)
}

