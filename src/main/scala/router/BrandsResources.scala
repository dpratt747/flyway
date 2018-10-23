package router


import io.finch._
import io.finch.syntax._
import schemas.{Brand, BrandType, Vendor}
import shapeless.{:+:, CNil}
import io.finch.circe._
import router.handlers.{BrandHandler, BrandTypesHandler}
import router.twitter_custom.TwitterConversion
import finch_custom.DecodeEncode._

import scala.concurrent.ExecutionContext.Implicits.global
import runner.LogTrait

class BrandsResources extends TwitterConversion with LogTrait {


  private val addBrand = post("addbrand" :: jsonBody[Brand]) { brand: Brand =>
    log.info("Request made to /addbrand")
    BrandHandler.addBrand(brand).asTwitter.map(Ok)
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /addbrand: $e")
      InternalServerError(e)
  }

  private val removeBrand = post("brandtypes" :: jsonBody[BrandType]){ brandType: BrandType =>
    log.info("Request made to /brandtypes")
    BrandTypesHandler.addBrandType(brandType).asTwitter.map(Ok)
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /brandTypes: $e")
      InternalServerError(e)
  }

  val brandTypeEndpoint: Endpoint[Seq[String] :+: Int :+: CNil] = addBrand
}
