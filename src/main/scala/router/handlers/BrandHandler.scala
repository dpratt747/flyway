package router.handlers

import akka.Done
import runner.{ItemFailedToDelete, ItemFailedToUpdate}
import schemas.Brand
import services.BrandService

import scala.concurrent.Future

object BrandHandler {


  val brandService = new BrandService
  private def areSizesEqual[A <: Seq[Any]](a: A, b:A): Boolean = {
    a.size.equals(b.size)
  }

  def addBrand(brand: Brand): Future[Brand] = {
    brandService.insertBrand(brand)
  }

  def deleteBrand(userID: Int, brandNames: Set[String]): Future[Done] = {
    val names = Future.sequence(brandNames.map{ name =>
      brandService.deleteBrandByUserIDAndName(userID, name).collect{case Some(e) => e}
    })
    names.map{ set =>
      if (!areSizesEqual(set, brandNames)) {
        val intersect = set.intersect(brandNames)
        val failed = brandNames.diff(set)
        throw ItemFailedToDelete(s"Failed to delete the following items: ${failed.mkString(", ")}. These items deleted " +
          s"successfully: ${intersect.mkString(", ")}")
      }
      Done
    }
  }

  def updateBrand(userID: Int, brand: Brand): Future[Done] = {
    brandService.updateBrand(userID, brand).map{
      case 0 => throw ItemFailedToUpdate(s"Failed to update item: $brand")
      case _ => Done
    }
  }

}