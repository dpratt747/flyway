package Runner

import schemas.{BrandTypes, Brands, BrandsContactDetails}
import slick.lifted.TableQuery

object brands extends TableQuery(new Brands(_))
object brandContactDetails extends TableQuery(new BrandsContactDetails(_))
object brandTypes extends TableQuery(new BrandTypes(_))

