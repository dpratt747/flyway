package router

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import io.finch.Input
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSpec}
import org.scalatest.concurrent.ScalaFutures
import schemas.Vendor

import scala.concurrent.Future

class VendorResourcesSpec extends FunSpec with BeforeAndAfter with MockFactory with ScalaFutures {
//  describe("A VendorService"){
//    mockDB.insertVendor _ when vendorForInsertion returns Future(Vendor( Some(1), "dpratt747", "dpratt747@gmail.com", "password", None, None))
//
//    it("should persist a vendor and return the same vendor object with an uniqueID") {
//      whenReady(mockDB.insertVendor(vendorForInsertion)) { result =>
//        assert(result.userName == "dpratt747")
//        assert(result.userID.contains(1))
//      }
//    }
//
//  }

  describe("A VendorResources"){
    val vendorService = new VendorResources

    it("should return pong when endpoint /pong is called"){
      val call = Input.get("/ping")
    }
  }
}
