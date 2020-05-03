package $package$.$name;format="camel"$.filters

import java.util.concurrent.atomic.AtomicReference

import org.joda.time.{DateTime, Seconds}
import play.api.mvc.{ActionFilter, Request, Result, Results}

import scala.concurrent.{ExecutionContext, Future}

/**
 * Simple implementation for a throttling filter for a Play Controller.
 */
class RateLimiterFilter(delay: Int)(implicit ec: ExecutionContext)
  extends ActionFilter[Request] with Results {

  private val ref = new AtomicReference[DateTime](null)
  private val tooManyRequests = TooManyRequests("Slow down a bit, please!")

  override protected def filter[A](request: Request[A]): Future[Option[Result]] = {
    val end = DateTime.now()
    val result = Option(ref.updateAndGet(chooseTime(_, end))) match {
      case Some(updated) if !updated.equals(end) => Some(tooManyRequests)
      case _ => None
    }
    Future.successful(result)
  }

  private def chooseTime(maybeStart: DateTime, end: DateTime): DateTime =
    Option(maybeStart) match {
      case Some(start) if Seconds.secondsBetween(start, end).getSeconds < delay => start
      case _ => end
    }

  override protected def executionContext: ExecutionContext = ec
}
