package services

import akka.Done
import runner.brandsTable
import schemas.Brand

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._

class BrandService {

  def updateBrand(brand: Brand): Future[Int] = {
    val query = brandsTable.filter(_.userID === brand.userID).insertOrUpdate(brand)
    db.run(query)
  }

  def getBrand(brandId: Int): Future[Option[Brand]] = {
    val query = brandsTable.filter(_.brandID === brandId).take(1).result.headOption
    db.run(query)
  }

  def insertBrand(inputBrand: Brand): Future[Brand] = {
    val query = brandsTable.returning(brandsTable.map(_.brandID)) into ((original, id) =>
      original.copy(brandID = Some(id))) += inputBrand
    db.run(query)
  }

  def deleteBrandByUserIDAndName(userID: Int, brandName: String): Future[Option[String]] = {
    val query = brandsTable.filter { table =>
      table.userID === userID
      table.brandName === brandName
    }
    val action = for {
      results <- query.map(_.brandName).result.headOption
      _ <- query.delete
    } yield results

    db.run(action)
  }

  val db = Database.forConfig("mariadb")
}
