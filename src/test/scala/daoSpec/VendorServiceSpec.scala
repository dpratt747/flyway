package daoSpec

import org.scalamock.scalatest.MockFactory
import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter
import services.VendorService
import schemas.Vendor

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import org.scalatest.concurrent.ScalaFutures

class VendorServiceSpec extends FunSpec with BeforeAndAfter with MockFactory with ScalaFutures {

  private val mockDB = stub[VendorService]
  private val vendorForInsertion = Vendor(None,"dpratt747", "dpratt747@gmail.com", "password")

  describe("A VendorService"){
    mockDB.insertVendor _ when vendorForInsertion returns Future(Vendor( Some(1), "dpratt747", "dpratt747@gmail.com", "password"))

    it("should persist a vendor and return the same vendor object with an uniqueID") {
      whenReady(mockDB.insertVendor(vendorForInsertion)) { result =>
        assert(result.userName == "dpratt747")
        assert(result.userID.contains(1))
      }
    }

  }
}
