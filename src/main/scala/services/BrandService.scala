package services

import akka.Done
import runner.brands
import schemas.Brand

import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._

class BrandService {

  def updateBrand(userId: Int, brand: Brand): Future[Int] = {
    val query = brands.filter(_.userID === userId).insertOrUpdate(brand)
    db.run(query)
  }

  def getBrand(brandId: Int): Future[Option[Brand]] = {
    val query = brands.filter(_.brandID === brandId).take(1).result.headOption
    db.run(query)
  }

  def insertBrand(inputBrand: Brand): Future[Brand] = {
    val query = brands.returning(brands.map(_.brandID)) into ((original, id) =>
      original.copy(brandID = Some(id))) += inputBrand
    db.run(query)
  }

  def deleteBrandByUserIDAndName(userID: Int, brandName: String): Future[Option[String]] = {
    val query = brands.filter { table =>
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
