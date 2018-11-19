package router.handlers

import com.github.t3hnar.bcrypt._
import runner.LogTrait

object Hash extends LogTrait{

  def encryptPassword(password: String): String = {
    val salt: String = generateSalt
    val hash = password.bcrypt(salt)
    log.info(s"encrypted password: $hash")
    hash
  }

  def isPasswordEqualToHash(password: String, hash: String): Boolean = password.isBcrypted(hash)

}

