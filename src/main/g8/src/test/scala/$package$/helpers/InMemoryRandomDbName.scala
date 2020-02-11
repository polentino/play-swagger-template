package $package$.$name;format="camel"$.helpers

import java.util.UUID

import com.typesafe.config.ConfigFactory
import $package$.$name;format="camel"$.slick.{GenericDbConfiguration, LiquibaseSchemaUpdater, SlickService}
import play.api.Configuration

trait InMemoryRandomDbName {
  val config: Configuration = Configuration(
    ConfigFactory.parseString(
      s"""slick {
         |
         |  $name;format="camel"$ {
         |    profile = "slick.jdbc.H2Profile\$\$"
         |    connectionPool = "HikariCP"
         |    keepAliveConnetion = true
         |    queueSize = -1
         |    numThreads = 10
         |    connectionTimeout = 10000
         |    maxConnections = 5
         |
         |    db {
         |      changelog = "dbchangelogs/db.changelog-master.xml"
         |      driverClassName = "org.h2.Driver"
         |      url = "jdbc:h2:mem:$name;format="camel"$-\${UUID
           .randomUUID()};database_to_upper=false;ignorecase=false;DB_CLOSE_DELAY=-1;MODE=MySql"
         |      username = "testuser"
         |      password = "testpassword"
         |    }
         |  }
         |}""".stripMargin
    )
  )

  val genericDBConfiguration = new GenericDbConfiguration("$name;format="camel"$", config)
  val service = new SlickService(
    new LiquibaseSchemaUpdater(genericDBConfiguration),
    genericDBConfiguration
  )
}
