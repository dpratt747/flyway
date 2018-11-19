package router


import io.finch._
import io.finch.syntax._
import schemas.Brand
import shapeless.{:+:, CNil}
import io.finch.circe._
import router.handlers.BrandHandler
import router.twitter_custom.TwitterConversion
import finch_custom.DecodeEncode._
import router.finch_custom.DecodeEncodeCustomFormat

import scala.concurrent.ExecutionContext.Implicits.global
import runner.LogTrait

class BrandsResources extends TwitterConversion with LogTrait with DecodeEncodeCustomFormat {


  private val addBrand = post("addbrand" :: jsonBody[Brand]) { brand: Brand =>
    log.info("Request made to /addbrand")
    BrandHandler.addBrand(brand).asTwitter.map{ res =>
      log.info(s"Request to /addbrand successful: $res")
      Ok(res)
    }
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /addbrand: $e")
      InternalServerError(e)
  }

  private val updateBrand = post("updatebrand" :: jsonBody[Brand]) { brand: Brand =>
    log.info("Request made to /updatebrand")
    BrandHandler.updateBrand(brand).asTwitter.map{ res =>
      log.info(s"Request to /updatebrand successful: $res")
      Ok(res)
    }
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /updatebrand: $e")
      InternalServerError(e)
  }

  private val removeBrand = post("removebrand" :: jsonBody[Brand] :: param[Int]("userID")){ (brand: Brand, id: Int) =>
    log.info("Request made to /removebrand")
    BrandHandler.deleteBrand(id, brand).asTwitter.map{ res =>
      log.info(s"Request to /brandtypes successful: $res")
      Ok(res)
    }
  } handle {
    case e: Exception =>
      log.error(s"Error during request to /brandTypes: $e")
      InternalServerError(e)
  }

  val brandTypeEndpoint: Endpoint[Brand :+: Int :+: CNil] = addBrand :+: removeBrand
}
