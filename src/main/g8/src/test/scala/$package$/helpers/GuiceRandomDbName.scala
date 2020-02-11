package $package$.$name;format="camel"$.helpers

import com.typesafe.scalalogging.LazyLogging
import org.scalatest.TestSuite
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

import scala.util.Random

/**
  * Trait to be used when you need to ensure different H2 instances are used in your tests.
  */
trait GuiceRandomDbName extends GuiceOneAppPerSuite with LazyLogging {
  this: TestSuite =>

  val defaultDbName = "$name;format="camel"$"
  val defaultConfigPath = "slick.$name;format="camel"$.db.url"

  override def fakeApplication(): Application = {
    val defaultUrl = super.fakeApplication().configuration.get[String](defaultConfigPath)
    val updatedUrl =
      defaultUrl.replace(defaultDbName, s"\$defaultDbName-\${Random.nextInt(Integer.MAX_VALUE)}")
    logger.info(s"changing default DB url connection into \$updatedUrl")

    GuiceApplicationBuilder().configure(defaultConfigPath -> updatedUrl).build()
  }
}
