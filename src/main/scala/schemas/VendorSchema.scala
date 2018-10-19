package schemas

import java.util.Date

import slick.jdbc.MySQLProfile.api._

case class Vendor(userID: Option[Int], userName: String, email: String, password: String, insertionDate: Option[Date], lastAccessedDate: Option[Date])

class Vendors(tag: Tag) extends Table[Vendor](tag, "vendor") {

  def userID = column[Int]("UserID", O.PrimaryKey, O.AutoInc)
  def userName = column[String]("UserName", O.SqlType("VARCHAR(100)"))
  def email = column[String]("Email", O.SqlType("VARCHAR(100)"))
  def password = column[String]("Password", O.SqlType("VARCHAR(100)"))
  def insertionDate = column[Date]("InsertionDate", O.SqlType("TIMESTAMP"))(DateWrapper.utilDate2SqlTimestampMapper)
  def lastAccessedDate = column[Date]("LastAccessedDate", O.SqlType("TIMESTAMP"))(DateWrapper.utilDate2SqlTimestampMapper)

  def * = (userID.?, userName, email, password, insertionDate.?, lastAccessedDate.?) <> (Vendor.tupled, Vendor.unapply)
}

object DateWrapper {

  val utilDate2SqlTimestampMapper = MappedColumnType.base[java.util.Date, java.sql.Timestamp](
  { utilDate => new java.sql.Timestamp(utilDate.getTime) },
  { sqlTimestamp => new java.util.Date(sqlTimestamp.getTime) })

}