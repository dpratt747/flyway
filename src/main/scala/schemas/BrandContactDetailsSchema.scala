package schemas

import slick.jdbc.MySQLProfile.api._

case class BrandContactDetails(contactID: Option[Int], phoneNumber: String, address: String)

class BrandsContactDetails (tag: Tag) extends Table[BrandContactDetails](tag, "brand_contact_details") {

def contactID = column[Int]("ContactID", O.PrimaryKey, O.AutoInc)
def phoneNumber = column[String]("PhoneNumber")
def address = column[String]("Address")
def * = (contactID.?, phoneNumber, address) <> (BrandContactDetails.tupled, BrandContactDetails.unapply)

}

