package router.handlers

import schemas.BrandType
import services.BrandTypeService

import scala.concurrent.Future

object BrandTypesHandler {

  private val brandTypeService = new BrandTypeService

  def getBrandTypesList: Future[Seq[String]] = {
    brandTypeService.getBrandTypes
  }

  def addBrandType(brandType: BrandType): Future[Int] = {
    brandTypeService.insertBrandType(brandType)
  }


}
