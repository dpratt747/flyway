package router.finch_custom

import java.sql.Timestamp

import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}
import runner.LogTrait

trait DecodeEncodeCustomFormat extends LogTrait {
  implicit val TimestampFormat : Encoder[Timestamp] with Decoder[Timestamp] = new Encoder[Timestamp] with Decoder[Timestamp] {
    override def apply(a: Timestamp): Json = {
      log.trace(s"encoding $a")
      Encoder.encodeString(a.toString)
    }

    override def apply(c: HCursor): Result[Timestamp] = {
      log.trace(s"decoding $c")
      Decoder.decodeLong.map(s => new Timestamp(s)).apply(c)
    }
  }
}
