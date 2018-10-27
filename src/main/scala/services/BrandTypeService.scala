package services


import runner.brandTypesTable
import schemas.BrandType

import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._

class BrandTypeService {

  def insertBrandType(b: BrandType): Future[Int] = {
    val query = brandTypesTable.insertOrUpdate(b)
    db.run(query)
  }

  def getBrandTypes: Future[Seq[String]] = {
      val query = brandTypesTable.map(_.typeName).result
      db.run(query)
  }


  val db = Database.forConfig("mariadb")
}
