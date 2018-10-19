package router

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import schemas.Vendor
import schemas.BrandType

object DecodeEncode {


  implicit val decodeVendor: Decoder[Vendor] = deriveDecoder[Vendor]
  implicit val encodeVendor: Encoder[Vendor] = deriveEncoder[Vendor]
  implicit val decodeBrandType: Decoder[BrandType] = deriveDecoder[BrandType]
  implicit val encodeBrandType: Encoder[BrandType] = deriveEncoder[BrandType]
}
