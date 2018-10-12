package crud

import runner.vendor
import schemas.Vendor
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

object Vendor {

  def insertVendor(inputVendor: Vendor): Future[Vendor] ={
    val query = vendor.returning(vendor.map(_.userID)) into ((a, b) =>
      a.copy(userID = Some(b))) += inputVendor
    db.run(query)
  }
  val db = Database.forConfig("mariadb")

}
