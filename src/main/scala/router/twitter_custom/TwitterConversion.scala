package router.twitter_custom

import com.twitter.util.{Return, Throw, Future => TFuture, Promise => TPromise}

import scala.concurrent.{ExecutionContext, Future => SFuture, Promise => SPromise}
import scala.util.{Failure, Success}

trait TwitterConversion {

  implicit class RichTFuture[A](f: TFuture[A]) {
    def asScala(implicit e: ExecutionContext): SFuture[A] = {
      val p: SPromise[A] = SPromise()
      f.respond {
        case Return(value) => p.success(value)
        case Throw(exception) => p.failure(exception)
      }

      p.future
    }
  }

  implicit class RichSFuture[A](f: SFuture[A]) {
    def asTwitter(implicit e: ExecutionContext): TFuture[A] = {
      val p: TPromise[A] = new TPromise[A]
      f.onComplete {
        case Success(value) => p.setValue(value)
        case Failure(exception) => p.setException(exception)
      }

      p
    }
  }

}
