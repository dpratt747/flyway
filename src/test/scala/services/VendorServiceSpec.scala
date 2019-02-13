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

  mockDB.getVendors _ when() returns Future(vendorList)
  mockDB.insertVendor _ when vendorForInsertion returns Future(Vendor(Some(1), "dpratt747", "dpratt747@gmail.com", "password", None, None))
  mockDB.getVendorByName _ when "dpratt747" returns Future(Some(Vendor(Some(1), "dpratt747", "dpratt747@gmail.com", "password", None, None)))
  mockDB.updateVendor _ when vendorForUpdate returns Future(1)
  mockDB.removeVendorByName _ when "dpratt747" returns Future(1)
  mockDB.removeVendorByName _ when "nonexistingvendor" returns Future(0)
  mockDB.removeVendorByID _ when 10000 returns Future(0)
  mockDB.removeVendorByID _ when 1 returns Future(1)

  describe("A VendorService") {

    it("should return a list of persisted vendors when getVendors method is called") {
      val test = mockDB.getVendors
      assert(test.futureValue.isInstanceOf[Seq[Vendor]])
    }

    it("should persist a vendor and return the same vendor object with an uniqueID") {
      val test = mockDB.insertVendor(vendorForInsertion)
      assert(test.futureValue.userName === "dpratt747")
      assert(test.futureValue.userID.contains(1))
    }
  }

  it("should return a persisted vendor when getVendor method is called") {
    val test = mockDB.getVendorByName("dpratt747")
    val getOption = test.futureValue.get
    assert(getOption.userName === "dpratt747")
    assert(getOption.userID.contains(1))
  }

  it("should return an int representing the number of affected rows when updateVendor method is called") {
    val test = mockDB.updateVendor(vendorForUpdate)
    assert(test.futureValue === 1)
  }

  it("should return an int of the updated vendor when updateVendor method is called") {
    val test = mockDB.getVendorByName("dpratt747")
    val getOption = test.futureValue.get
    assert(getOption.userName === "dpratt747")
    assert(getOption.userID.contains(1))
  }

  it("should return 0 when removeVendorByID method is called with non-existing vendor") {
    val test = mockDB.removeVendorByID(10000)
    assert(test.futureValue === 0)
  }

  it("should return 1 when removeVendorByID method is called with an existing vendor") {
    val test = mockDB.removeVendorByID(1)
    assert(test.futureValue === 1)
  }

  it("should return 0 when removeVendorByName method is called with non-existing vendor") {
    val test = mockDB.removeVendorByName("nonexistingvendor")
    assert(test.futureValue === 0)
  }

  it("should return 1 when removeVendorByName method is called with existing vendor") {
    val test = mockDB.removeVendorByName("dpratt747")
    assert(test.futureValue === 1)
  }
}
