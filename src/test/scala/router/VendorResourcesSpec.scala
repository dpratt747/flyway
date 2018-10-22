package router

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import io.finch.{Endpoint, Input}
import io.finch._
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSpec, PrivateMethodTester}
import org.scalatest.concurrent.ScalaFutures
import schemas.Vendor

import scala.concurrent.Future

class VendorResourcesSpec extends FunSpec with BeforeAndAfter with MockFactory with ScalaFutures with PrivateMethodTester {
//  private val mockvendorService = stub[VendorResources]
//  mockvendorService.ping _ when() returns("pong")
//
//  private val ping = PrivateMethod[Endpoint[String]]('ping)
//
////  describe("A VendorService"){
////    mockDB.insertVendor _ when vendorForInsertion returns Future(Vendor( Some(1), "dpratt747", "dpratt747@gmail.com", "password", None, None))
////
////    it("should persist a vendor and return the same vendor object with an uniqueID") {
////      whenReady(mockDB.insertVendor(vendorForInsertion)) { result =>
////        assert(result.userName == "dpratt747")
////        assert(result.userID.contains(1))
////      }
////    }
////
////  }
//
//  describe("A VendorResources"){
//    it("should return pong when endpoint /pong is called"){
//      mockvendorService invokePrivate ping(Input.get("/ping"))
//    }
//  }
}
