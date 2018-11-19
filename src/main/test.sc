import java.security.{MessageDigest, SecureRandom}


val a = List(1,12,2,2,2,3,3,3,121)
val b = a.reverse
a.contains(2)
b.equals(a)
1 :: a

def generateSalt = {
  val sr = SecureRandom.getInstance("SHA1PRNG")
  val salt: Array[Byte] = Array(16)
  sr.nextBytes(salt)
  salt
}

generateSalt
