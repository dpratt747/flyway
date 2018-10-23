package services

import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSpec, MustMatchers}

import scala.concurrent.Await._
import scala.concurrent.duration._

import schemas.Vendor

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import org.scalatest.concurrent.ScalaFutures


class VendorServiceSpec extends FunSpec with BeforeAndAfter with MockFactory with ScalaFutures {

  describe("A VendorService"){
    val mockDB = stub[VendorService]
    val vendorForInsertion = Vendor(None,"dpratt747", "dpratt747@gmail.com", "password", None, None)
    val vendorList: Seq[Vendor] = Seq(vendorForInsertion, vendorForInsertion)
    mockDB.insertVendor _ when vendorForInsertion returns Future(Vendor( Some(1), "dpratt747", "dpratt747@gmail.com", "password", None, None))
    mockDB.getVendors _ when() returns Future(vendorList)

    it("should persist a vendor and return the same vendor object with an uniqueID") {
      mockDB.insertVendor(vendorForInsertion) map { result =>
        assert(result.userName == "dpratt747")
        assert(result.userID.contains(1))
      }
    }

    it("should return a list of persisted vendors when getVendor method is called") {
      mockDB.getVendors map { result =>
        assert(result.isInstanceOf[Seq[Vendor]])
      }
    }
  }

}
