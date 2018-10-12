package runner

import crud.ContactDetails._
import crud.Vendor._
import schemas.{BrandContactDetails, Vendor}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.concurrent.duration._


object Main extends App {
  val a = BrandContactDetails(None, "07873327004" , "Fake Address Should add some addres authentication in the future maybe" )
  val b = Vendor(None,"dpratt747", "dpratt747@gmail.com", "password")


  Await.result( insertVendor(b).map(println(_)), 10 seconds)
  Await.result( insertContactDetails(a).map(println(_)), 10 seconds)
}
