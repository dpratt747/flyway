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

  def getVendors: Future[Seq[Vendor]] = {
    vendorService.getVendors
  }

}