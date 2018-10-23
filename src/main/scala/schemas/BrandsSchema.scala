package schemas

import runner.{brandContactDetails, brandTypes, vendor}
import slick.jdbc.MySQLProfile.api._

case class Brand(brandID: Option[Int], brandName: String, typeID: Int, contactDetailsID: Int, userID: Int)

class Brands(tag: Tag) extends Table[Brand](tag, "brands") {

  def brandID = column[Int]("BrandID", O.PrimaryKey, O.AutoInc)
  def brandName = column[String]("BrandName", O.Unique)
  def typeID = column[Int]("TypeID")
  def contactDetailsID = column[Int]("ContactDetailsID")
  def userID = column[Int]("UserID")

  def * = (brandID.?, brandName, typeID, contactDetailsID, userID) <> (Brand.tupled, Brand.unapply)

  foreignKey("TYPE_FK", typeID, brandTypes)(_.typeID, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
  foreignKey("TYPE_FK", typeID, brandContactDetails)(_.contactID, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
  foreignKey("TYPE_FK", userID, vendor)(_.userID, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

}
