package router.handlers

import java.security.MessageDigest
import java.sql.Timestamp
import java.util.{Calendar, Date}
import java.time._

import schemas.Vendor
import services.VendorService

object VendorHandler {

  def addVendor(vendor: Vendor) = {
    val currentDate = Calendar.getInstance().getTime
    Timestamp.valueOf(currentDate)
    if(vendor.insertionDate.isEmpty) vendor.copy(insertionDate = Some(Date)

    VendorService.insertVendor()
  }

  def encryptPassword(string: String): String = {
    val digest = MessageDigest.getInstance("MD5")
    digest.update(string.getBytes())
    digest.digest().map { b =>
      ((b & 0xff) + 0x100, 16).toString().substring(1)
    }.toString
  }


}