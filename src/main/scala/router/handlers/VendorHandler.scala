package router.handlers

import java.security.MessageDigest
import java.sql.Timestamp
import java.util.{Calendar, Date}
import java.time._

import schemas.Vendor
import services.VendorService

import scala.concurrent.Future

object VendorHandler {


  private val vendorService = new VendorService

  def addVendor(vendor: Vendor): Future[Vendor] = {
    val currentDate = new Timestamp(Calendar.getInstance.getTime.getTime)
    val vendorWithDates = vendor.copy(insertionDate = Some(currentDate), lastAccessedDate = Some(currentDate))
    vendorService.insertVendor(vendorWithDates)
  }

  def encryptPassword(string: String): String = {
    val digest = MessageDigest.getInstance("MD5")
    digest.update(string.getBytes())
    digest.digest().map { b =>
      ((b & 0xff) + 0x100, 16).toString().substring(1)
    }.toString
  }

  def getVendors: Future[Seq[Vendor]] = {
    vendorService.getVendors
  }

}