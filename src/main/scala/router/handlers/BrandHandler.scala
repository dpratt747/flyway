package router.handlers

import akka.Done
import runner.ItemFailedToDelete
import schemas.Brand
import services.BrandService

import scala.concurrent.Future

object BrandHandler {


  val brandService = new BrandService

  def addBrand(brand: Brand): Future[Brand] = {
    brandService.insertBrand(brand)
  }

  def deleteBrand(userID: Int, brandNames: Set[String]): Future[Done] = {
    val names = Future.sequence(brandNames.map{ name =>
      brandService.deleteBrandByUserIDAndName(userID, name).collect{case Some(e) => e}
    })
    names.map{ set =>
      if (set.size != brandNames.size) {
        val intersection = set.intersect(brandNames)
        val failed = brandNames.diff(set)
        throw ItemFailedToDelete(s"")
      }

    }
//    brandName.flatMap(brandService.deleteBrandByUserIDAndName(userID, _))

  }
}
