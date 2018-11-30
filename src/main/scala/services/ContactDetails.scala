package services

import runner.brandContactDetailsTable
import schemas.BrandContactDetails
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.Future



class ContactDetailsService {


  def insertContactDetails(details: BrandContactDetails): Future[BrandContactDetails] = {
    val query = brandContactDetailsTable.returning(brandContactDetailsTable.map(_.contactID)) into ((contactDetails, id) =>
      contactDetails.copy(contactID = Some(id))) += details
    db.run(query)
  }

//  def updateContactDetails(): Future[BrandContactDetails] = {}


  private val db = ConnectionFactory("mariadb").loadMysql
}
