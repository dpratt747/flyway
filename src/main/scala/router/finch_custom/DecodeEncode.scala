package router.finch_custom

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import schemas.{BrandType, Vendor}

object DecodeEncode extends DecodeEncodeCustomFormat {

  implicit val decodeVendor: Decoder[Vendor] = deriveDecoder[Vendor]
  implicit val encodeVendor: Encoder[Vendor] = deriveEncoder[Vendor]
  implicit val decodeBrandType: Decoder[BrandType] = deriveDecoder[BrandType]
  implicit val encodeBrandType: Encoder[BrandType] = deriveEncoder[BrandType]

}
