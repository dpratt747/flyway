package router


import io.finch._
import io.finch.syntax._
import schemas.BrandType
import shapeless.{:+:, CNil}
import io.finch.circe._
import router.handlers.BrandTypesHandler
import router.twitter_custom.TwitterConversion
import finch_custom.DecodeEncode._
import router.finch_custom.DecodeEncodeCustomFormat

import scala.concurrent.ExecutionContext.Implicits.global
import runner.LogTrait

class BrandTypeResources extends TwitterConversion with LogTrait with DecodeEncodeCustomFormat {


  private val getBrandTypes = get("brandtypes"){
    log.info("Request made to /brandtypes")
    val typeList = BrandTypesHandler.getBrandTypesList
    typeList.asTwitter.map{ res =>
      log.info(s"Request to /brandTypes successful: $res")
      Ok(res)
    }
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /brandTypes: $e")
      InternalServerError(e)
  }

  private val addBrandType = post("brandtypes" :: jsonBody[BrandType]){ brandType: BrandType =>
    log.info("Request made to /brandtypes")
    BrandTypesHandler.addBrandType(brandType).asTwitter.map{ res =>
      log.info(s"Request to /brandtypes successful: $res")
      Ok(res)
    }
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /brandTypes: $e")
      InternalServerError(e)
  }

  val brandTypeEndpoint: Endpoint[Seq[String] :+: Int :+: CNil] = getBrandTypes :+: addBrandType
}
