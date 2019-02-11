package handlers

import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSpec, PrivateMethodTester}
import org.scalatest.concurrent.ScalaFutures
import router.handlers.VendorHandler
import schemas.Vendor
import services.VendorService
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class VendorHandlerSpec extends FunSpec with BeforeAndAfter with MockFactory with ScalaFutures with PrivateMethodTester {

  private val vendorToInsert = Vendor(None, "david", "emailaddress@..a.sasaad", "test2", None, None)
  private val vendorReturned = Vendor(Some(1), "david", "emailaddress@..a.sasaad", "test2", None, None)

  private val mockvendorService = stub[VendorService]
  mockvendorService.insertVendor _ when vendorToInsert returns Future(vendorReturned)

  describe("A VendorHandler") {
    it("should return a vendor when method addVendor is called") {
      val test = VendorHandler.addVendor(vendorToInsert)
      test.map { res =>
        assert(res == vendorReturned)
      }
    }

    it("test4") {
     assert(1 === 1)
    }
  }
}
