package $package$.$name;format="camel"$.slick

import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.LazyLogging
import javax.inject.{Inject, Singleton}
import org.apache.commons.lang3.time.StopWatch
import slick.dbio.{DBIOAction, NoStream}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Service to be mixed into any '''Repository''' class, to lazily update the schema and give access to
  * the [[run()]] method, needed to perform SQL operations.
  *
  * @param updater
  */
@Singleton
class SlickService @Inject()(updater: LiquibaseSchemaUpdater, configuration: GenericDbConfiguration)
    extends LazyLogging {

  lazy val db = {
    logger.info("Updating DB")
    val stopWatch = StopWatch.createStarted()
    updater.update()
    logger.info(s"DB updated in \${stopWatch.getTime(TimeUnit.MILLISECONDS)} ms")

    configuration.jdbcProfile
  }

  def run[R](action: DBIOAction[R, NoStream, Nothing]): Future[R] = {
    db.db.run(action).recoverWith {
      case exception: Throwable =>
        logger.error("query failed", exception)
        Future.failed[R](exception)
    }
  }
}
