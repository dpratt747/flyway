package services

import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfter, FunSpec, OneInstancePerTest}
import schemas.UserAccount

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class UserAccountServiceSpec extends FunSpec with BeforeAndAfter with MockFactory with ScalaFutures with OneInstancePerTest {

	private val userAccountForInsertion = UserAccount(None, "dpratt747", "dpratt747@gmail.com", "password", None, None)
	private val userAccountForUpdate = UserAccount(Some(1), "dpratt747", "dpratt747@mailinator.com", "updatedPassword", None, None)
	private val userAccountList: Seq[UserAccount] = Seq(userAccountForInsertion, userAccountForInsertion, userAccountForInsertion)
	private val mockDB = stub[UserAccountService]

	mockDB.deleteUserAccountById _ when 1 returns Future(Some("dpratt747"))
	mockDB.deleteUserAccountById _ when 0 returns Future(None)
	mockDB.deleteUserAccountByName _ when "dpratt747" returns Future(Some("dpratt747"))
	mockDB.deleteUserAccountByName _ when "not-existing" returns Future(None)
	mockDB.insertUserAccount _ when userAccountForInsertion returns Future(UserAccount(Some(1), "dpratt747", "dpratt747@gmail.com", "password", None, None))
	mockDB.getUserAccountByEmail _ when "dpratt747@gmail.com" returns Future(Some(userAccountForInsertion))
	mockDB.getUserAccountByEmail _ when "not-existing@email.com" returns Future(None)
	mockDB.getUserAccountByName _ when "dpratt747" returns Future(Some(userAccountForInsertion))
	mockDB.getUserAccountByName _ when "not-existing" returns Future(None)
	mockDB.getUserAccountById _ when 1 returns Future(Some(userAccountForInsertion))
	mockDB.getUserAccountById _ when 0 returns Future(None)
	mockDB.getUserAccounts _ when() returns Future(userAccountList)
	mockDB.updateUserAccount _ when userAccountForUpdate returns Future(1)


	describe ("A UserAccountService") {
		it("should persist a user and return the same user object with an uniqueID") {
			mockDB.insertUserAccount(userAccountForInsertion) map { result =>
				assert(result.userName === "dpratt747")
				assert(result.userID.contains(1))
			}
		}
	}
}
