package services

import runner.vendor
import schemas.Vendor
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent._
import scala.concurrent.ExecutionContext.global


class VendorService() {

  def insertVendor(inputVendor: Vendor): Future[Vendor] ={
    val query = vendor.returning(vendor.map(_.userID)) into ((a, b) =>
      a.copy(userID = Some(b))) += inputVendor
    db.run(query)
  }

//  override def getVendorByID(id: Int): Future[Vendor] = ???
//
//  override def getVendorByUsername(username: String): Future[Vendor] = ???
//
//  override def updateVendor(vendor: Vendor): Future[Vendor] = ???

  val db = Database.forConfig("mariadb")
}
