package runner

import schemas.{BrandTypes, Brands, BrandsContactDetails, Vendors}
import slick.lifted.TableQuery

object brands extends TableQuery(new Brands(_))
object brandContactDetails extends TableQuery(new BrandsContactDetails(_))
object brandTypes extends TableQuery(new BrandTypes(_))
object vendor extends TableQuery(new Vendors(_))
