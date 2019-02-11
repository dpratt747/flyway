package services

import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfter, FunSpec, OneInstancePerTest}
import schemas.Vendor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class VendorServiceSpec extends FunSpec with BeforeAndAfter with MockFactory with ScalaFutures with OneInstancePerTest {

	private val vendorForInsertion = Vendor(None, "dpratt747", "dpratt747@gmail.com", "password", None, None)
	private val vendorForUpdate = Vendor(Some(1), "dpratt747", "dpratt747@gmail.com", "updatedPassword", None, None)
	private val vendorList: Seq[Vendor] = Seq(vendorForInsertion, vendorForInsertion)
	private val mockDB = stub[VendorService]

	mockDB.getVendors _ when () returns Future(vendorList)
	mockDB.insertVendor _ when vendorForInsertion returns Future(Vendor(Some(1), "dpratt747", "dpratt747@gmail.com", "password", None, None))
	mockDB.getVendorByName _ when "dpratt747" returns Future(Some(Vendor(Some(1), "dpratt747", "dpratt747@gmail.com", "password", None, None)))
	mockDB.updateVendor _ when vendorForUpdate returns Future(1)
	mockDB.removeVendorByName _ when "dpratt747" returns Future(1)
	mockDB.removeVendorByName _ when "nonexistingvendor" returns Future(0)

	describe("A VendorService") {

		it("should return a list of persisted vendors when getVendors method is called") {
			mockDB.getVendors map { result =>
				assert(result.isInstanceOf[Seq[Vendor]])
			}
		}

		it("should persist a vendor and return the same vendor object with an uniqueID") {
			mockDB.insertVendor(vendorForInsertion) map { result =>
				assert(result.userName === "dpratt747")
				assert(result.userID.contains(1))
			}
		}

		it("should return a persisted vendor when getVendor method is called") {
			mockDB.getVendorByName("dpratt747") map { result =>
				val getOption = result.get
				assert(getOption.userName === "dpratt747")
				assert(getOption.userID.contains(1))
			}
		}

		it("should return an int representing the number of affected rows when updateVendor method is called") {
			mockDB.updateVendor(vendorForUpdate) map { result =>
				assert(result === 1)
			}
		}

		it("should return an int of the updated vendor when updateVendor method is called") {
			mockDB.getVendorByName("dpratt747") map { result =>
				val getOption = result.get
				assert(getOption.userName === "dpratt747")
				assert(getOption.userID.contains(1))
			}
		}

		it("should return 0 when removeVendor method is called with non-existing vendor") {
			mockDB.removeVendorByName("nonexistingvendor") map { result =>
				assert(result === 0)
			}
		}

		it("should return 1 when removeVendor method is called with existing vendor") {
			mockDB.removeVendorByName("dpratt747") map { result =>
				assert(result === 1)
			}
		}
	}
}
