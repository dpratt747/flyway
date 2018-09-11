package schema

//import slick.lifted.Tag
//import slick.model.Table
import slick.jdbc.MySQLProfile.api._

case class BrandContactDetails(contactID: Int, phoneNumber: Int, address: String)

class BrandsContactDetails (tag: Tag) extends Table[BrandContactDetails](tag, "brand_type") {

def contactID = column[Int]("ContactID", O.PrimaryKey, O.AutoInc)
def phoneNumber = column[Int]("PhoneNumber")
def address = column[String]("Address")
def * = (contactID, phoneNumber, address) <> (BrandContactDetails.tupled, BrandContactDetails.unapply)

}

object brandContactDetails extends TableQuery(new BrandsContactDetails(_))