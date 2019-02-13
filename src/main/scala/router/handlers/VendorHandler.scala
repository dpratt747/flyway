package router.handlers

import java.sql.Timestamp
import java.util.Calendar

import schemas.Vendor
import services.VendorService
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object VendorHandler {


  private val vendorService = new VendorService

  def addVendor(vendor: Vendor): Future[Vendor] = {
    val currentDate = new Timestamp(Calendar.getInstance.getTime.getTime)
    val vendorWithDates = vendor.copy(insertionDate = Some(currentDate), lastAccessedDate = Some(currentDate))
    vendorService.insertVendor(vendorWithDates)
  }
  def deleteVendorByName(username: String): Future[Int] = {
    vendorService.removeVendorByName(username)
  }

  def deleteVendorByID(id: Int): Future[Int] = {
    vendorService.removeVendorByID(id)
  }

  def getVendors: Future[Seq[Vendor]] = {
    vendorService.getVendors
  }

}