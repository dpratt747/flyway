package router.handlers

import com.github.t3hnar.bcrypt._

object PasswordHash {


  def encryptPassword(password: String): String = {
    val salt: String = generateSalt
    password.bcrypt(salt)
  }
  

}
