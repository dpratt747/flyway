package schema

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import slick.jdbc.MySQLProfile.api._
import java.sql.Date

import slick.lifted

import scala.reflect.ClassTag

case class BrandType(typeID: Option[Int], typeName: String)

class BrandTypes(tag: Tag) extends Table[BrandType](tag, "brand_type") {

  def typeID = column[Int]("TypeID", O.PrimaryKey, O.AutoInc)
  def typeName = column[String]("TypeName")
  def * = (typeID.?, typeName) <> (BrandType.tupled, BrandType.unapply)

}

object brandTypes extends TableQuery(new BrandTypes(_))

