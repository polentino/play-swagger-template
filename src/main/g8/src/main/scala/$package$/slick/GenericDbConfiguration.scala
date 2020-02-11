package $package$.$name;format="camel"$.slick

import javax.inject.{Inject, Named, Singleton}
import play.api.Configuration
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

/**
  * Object that gathers all configurations needed for liquibase/slick
  * @param schema
  * @param configuration
  */
@Singleton
class GenericDbConfiguration @Inject()(
    @Named("applicationSchema") schema: String,
    configuration: Configuration
  ) {

  private val cfg: Configuration = configuration.get[Configuration](s"slick.\$schema.db")

  val jdbcProfile: DatabaseConfig[JdbcProfile] =
    DatabaseConfig.forConfig[JdbcProfile](s"slick.\$schema", configuration.underlying)

  // required properties
  val changelog: String = cfg.get[String]("changelog")
  val url: String = cfg.get[String]("url")
  val username: String = cfg.get[String]("username")
  val password: String = cfg.get[String]("password")
  val driverClassName: String = cfg.get[String]("driverClassName")
  // optional ones
  val defaultCatalogName: String = cfg.getOptional[String]("defaultCatalogName").orNull
  val defaultSchemaName: String = cfg.getOptional[String]("defaultSchemaName").orNull
  val outputDefaultCatalog: Boolean =
    cfg.getOptional[Boolean]("outputDefaultCatalog").getOrElse(false)
  val outputDefaultSchema: Boolean =
    cfg.getOptional[Boolean]("outputDefaultSchema").getOrElse(false)
  val databaseClass: String = cfg.getOptional[String]("databaseClass").orNull
  val databasePropertiesFile: String = cfg.getOptional[String]("databasePropertiesFile").orNull
  val propertyProviderClass: String = cfg.getOptional[String]("propertyProviderClass").orNull
  val liquibaseCatalogName: String = cfg.getOptional[String]("liquibaseCatalogName").orNull
  val liquibaseSchemaName: String = cfg.getOptional[String]("liquibaseSchemaName").orNull
  val dbChangeLogTableName: String = cfg.getOptional[String]("databaseChangeLogTableName").orNull
  val dbChangeLogLockTableName: String =
    cfg.getOptional[String]("databaseChangeLogLockTableName").orNull
}
