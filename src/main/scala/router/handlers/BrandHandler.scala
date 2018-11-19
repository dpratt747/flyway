package router.handlers

import akka.Done
import runner.{ItemFailedToDelete, ItemFailedToUpdate}
import schemas.Brand
import services.BrandService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object BrandHandler {

  val brandService = new BrandService
  private def areSizesEqual[A <: Iterable[Any]](a: A, b:A): Boolean = {
    a.size.equals(b.size)
  }

  def addBrand(brand: Brand): Future[Brand] = {
    brandService.insertBrand(brand)
  }

  def deleteBrand(userId: Int, brand: Brand): Future[Done.type] = {
    brandService.deleteBrandByUserIDAndName(userId, brand.brandName).map(_ => Done)
  }

  def deleteBrands(userID: Int, brandNames: Set[String]): Future[Done] = {
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

  def updateBrand(brand: Brand): Future[Done] = {
    brandService.updateBrand(brand).map{
      case 0 => throw ItemFailedToUpdate(s"Failed to update item: $brand")
      case _ => Done
    }
  }

}