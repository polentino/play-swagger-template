package $package$.$name;format="camel"$.slick

import com.typesafe.scalalogging.LazyLogging
import javax.inject.{Inject, Singleton}
import liquibase.database.Database
import liquibase.integration.commandline.CommandLineUtils
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.{Contexts, Liquibase}

import scala.util.control.NonFatal

/**
  * Class responsible to keep our DB up to date with the latest changesets.
  * @param ddConfig
  */
@Singleton
class LiquibaseSchemaUpdater @Inject()(ddConfig: GenericDbConfiguration) extends LazyLogging {

  def update(): Unit = {
    var databaseObject: Database = null

    try {
      val classLoader = Thread.currentThread().getContextClassLoader
      val resourceAccessor = new ClassLoaderResourceAccessor(classLoader)
      databaseObject = CommandLineUtils.createDatabaseObject(
        resourceAccessor,
        ddConfig.url,
        ddConfig.username,
        ddConfig.password,
        ddConfig.driverClassName,
        ddConfig.defaultCatalogName,
        ddConfig.defaultSchemaName,
        ddConfig.outputDefaultCatalog,
        ddConfig.outputDefaultSchema,
        ddConfig.databaseClass,
        ddConfig.databasePropertiesFile,
        ddConfig.propertyProviderClass,
        ddConfig.liquibaseCatalogName,
        ddConfig.liquibaseSchemaName,
        ddConfig.dbChangeLogTableName,
        ddConfig.dbChangeLogLockTableName
      )
      val l = new Liquibase(ddConfig.changelog, resourceAccessor, databaseObject)
      l.update(new Contexts())
    } catch {
      case e: Throwable =>
        logger.error(s"For database at \${ddConfig.url}:", e)
    } finally {
      try {
        Option(databaseObject).map(_.close())
      } catch {
        case NonFatal(e) =>
          logger.error("An error occurred:", e)
      }
    }
  }
}
