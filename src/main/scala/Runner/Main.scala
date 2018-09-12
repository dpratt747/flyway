package Runner

import CRUD.ContactDetails._
import schemas.BrandContactDetails

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.concurrent.duration._


object Main extends App {
  val a = BrandContactDetails(None, "07873327004" , "Fake Address Should add some addres authentication in the future maybe" )
  Await.result( insertContactDetails(a).map(println(_)), 10 seconds)
}
