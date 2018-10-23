package router


import io.finch._
import io.finch.syntax._
import schemas.{BrandType, Vendor}
import shapeless.{:+:, CNil}
import io.finch.circe._
import router.handlers.BrandTypesHandler
import router.twitter_custom.TwitterConversion
import finch_custom.DecodeEncode._

import scala.concurrent.ExecutionContext.Implicits.global
import runner.LogTrait

class BrandTypeResources extends TwitterConversion with LogTrait {


  private val getBrandTypes = get("brandtypes"){
    log.info("Request made to /brandtypes")
    val typeList = BrandTypesHandler.getBrandTypesList
    typeList.map{ list =>
      log.info(s"Retrieved brand types list: $list")
    }
    typeList.asTwitter.map(Ok)
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /brandTypes: $e")
      InternalServerError(e)
  }

  private val addBrandType = post("brandtypes" :: jsonBody[BrandType]){ brandType: BrandType =>
    log.info("Request made to /brandtypes")
    BrandTypesHandler.addBrandType(brandType).asTwitter.map(Ok)
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /brandTypes: $e")
      InternalServerError(e)
  }

  val brandTypeEndpoint: Endpoint[Seq[String] :+: Int :+: CNil] = getBrandTypes :+: addBrandType
}
