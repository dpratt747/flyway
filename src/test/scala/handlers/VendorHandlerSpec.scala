package handlers

import org.scalamock.scalatest.{AsyncMockFactory, MockFactory}
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import router.handlers.VendorHandler
import schemas.Vendor
import services.{ConnectionFactory, VendorService}
import slick.jdbc.MySQLProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class VendorHandlerSpec extends FunSpec with BeforeAndAfter with MockFactory with ScalaFutures with PrivateMethodTester with OneInstancePerTest {

  private val vendorToInsert = Vendor(None, "david", "emailaddress@..a.sasaad", "test2", None, None)
  private val vendorReturned = Vendor(Some(1), "david", "emailaddress@..a.sasaad", "test2", None, None)
  private val vendorList = Seq(vendorReturned, vendorReturned, vendorReturned)



  describe("A VendorHandler") {

    before{

    }

    it("should return a vendor when method addVendor is called") {
      val test = VendorHandler.addVendor(vendorToInsert)
      assert(test.futureValue === vendorReturned)
    }

    it("should return the number of rows affected when a vendor is deleted by Name") {
      val test = VendorHandler.deleteVendorByName("dpratt747")
      assert(test.futureValue.isInstanceOf[Int])
    }

    it("should return the number of rows affected when a vendor is deleted by ID"){
      val test = VendorHandler.deleteVendorByID(1)
      assert(test.futureValue.isInstanceOf[Int])
    }

    it("should return a list of vendors when getVendors is called"){
      val test = VendorHandler.getVendors
      assert(test.futureValue === vendorList)
    }

  }
}
