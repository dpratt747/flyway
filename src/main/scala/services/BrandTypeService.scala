package services


import runner.brandTypes
import schemas.BrandType

import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._

object BrandTypeService {

  def insertBrandType(b: BrandType): Future[Int] = {
    val query = brandTypes.insertOrUpdate(b)
    db.run(query)
  }

  def getBrandTypes: Future[Seq[String]] = {
      val query = brandTypes.map(_.typeName).result
      db.run(query)
  }


  val db = Database.forConfig("mariadb")
}
