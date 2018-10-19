import java.security.MessageDigest


def encryptPassword(string: String): String = {
  val d = MessageDigest.getInstance("MD5")
  d.update(string.getBytes())
  val bytes = d.digest()
  println(bytes)
    bytes.map { b =>
//      val res = b & 0xff + 0x100
    b.&(0xff) + 0x100
      .toString.substring(1)
  }.mkString
}


val x = encryptPassword("password")
