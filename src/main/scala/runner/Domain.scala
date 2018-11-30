package runner

import schemas._
import slick.lifted.TableQuery

object brandsTable extends TableQuery(new Brands(_))
object brandContactDetailsTable extends TableQuery(new BrandsContactDetails(_))
object brandTypesTable extends TableQuery(new BrandTypes(_))
object vendorTable extends TableQuery(new Vendors(_))
object userAccountTable extends TableQuery(new UserAccounts(_))

final case class ItemFailedToDelete(private val message: String, private val cause: Throwable = None.orNull) extends Exception(message, cause)
final case class ItemFailedToUpdate(private val message: String, private val cause: Throwable = None.orNull) extends Exception(message, cause)
final case class FailedToFindUser(private val message: String, private val cause: Throwable = None.orNull) extends Exception(message, cause)
final case class InvalidDbCall(private val message: String, private val cause: Throwable = None.orNull) extends Exception(message, cause)



