package services

import runner.vendorTable
import schemas.Vendor
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class VendorService {

  def getVendors: Future[Seq[Vendor]] = {
    val query = vendorTable.result
    db.run(query)
  }

  def getVendorByName(name: String): Future[Option[Vendor]] = {
    val query = vendorTable.filter(_.userName === name).take(1).result.headOption
    db.run(query)
  }

  def insertVendor(inputVendor: Vendor): Future[Vendor] = {
    val query = vendorTable.returning(vendorTable.map(_.userID)) into ((a, b) =>
      a.copy(userID = Some(b))) += inputVendor
    db.run(query)
  }

  def updateVendor(inputVendor: Vendor): Future[Int] = {
    val query = vendorTable.insertOrUpdate(inputVendor)
    db.run(query)
  }

  def removeVendorByName(name: String): Future[Int] = {
    val query = vendorTable.filter(_.userName === name).delete
    db.run(query)
  }

  val db = Database.forConfig("mariadb")
}
