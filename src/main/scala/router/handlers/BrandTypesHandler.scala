package router.handlers

import schemas.BrandType
import services.BrandTypeService

import scala.concurrent.Future

object BrandTypesHandler {

  def getBrandTypesList: Future[Seq[String]] = {
    BrandTypeService.getBrandTypes
  }

  def addBrandType(brandType: BrandType): Future[Int] = {
    BrandTypeService.insertBrandType(brandType)
  }

}
