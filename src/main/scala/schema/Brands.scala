package schema

import slick.jdbc.MySQLProfile.api._
import schema.{brandTypes, brandContactDetails}

case class Brand(brandID: Option[Int], brandName: String, typeID: Int, contactDetailsID: Int)

class Brands(tag: Tag) extends Table[Brand](tag, "brand_type") {

  def brandID = column[Int]("BrandID", O.PrimaryKey, O.AutoInc)
  def brandName = column[String]("BrandName")
  def typeID = column[Int]("TypeID")
  def contactDetailsID = column[Int]("ContactDetailsID")

  def * = (brandID.?, brandName, typeID, contactDetailsID) <> (Brand.tupled, Brand.unapply)

  foreignKey("TYPE_FK", typeID, brandTypes)(_.typeID, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
  foreignKey("TYPE_FK", typeID, brandContactDetails)(_.contactID, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

}
  object brands extends TableQuery(new Brands(_))
