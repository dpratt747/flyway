package router

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import schemas.Vendor

object DecodeEncode {

  implicit val decodeVendor: Decoder[Vendor] = deriveDecoder[Vendor]
  implicit val encodeVendor: Encoder[Vendor] = deriveEncoder[Vendor]

}
