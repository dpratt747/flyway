package dao

import runner.brandContactDetails
import schemas.BrandContactDetails
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.Future



object ContactDetails {


  def insertContactDetails(details: BrandContactDetails): Future[BrandContactDetails] = {
    val query = brandContactDetails.returning(brandContactDetails.map(_.contactID)) into ((contactDetails, id) =>
      contactDetails.copy(contactID = Some(id))) += details
    db.run(query)
  }

//  def updateContactDetails(): Future[BrandContactDetails] = {}


  val db = Database.forConfig("mariadb")
}
