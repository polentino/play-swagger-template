package $package$.$name;format="camel"$.repositories

import javax.inject.{Inject, Singleton}
import $package$.$name;format="camel"$.persistence.{Name, NamesTable}
import $package$.$name;format="camel"$.slick.{SlickDatabase, SlickService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Repository class that will handle CRUD operations of [[$package$.$name;format="camel"$.persistence.Name Name]]
  * instances over a [[$package$.$name;format="camel"$.persistence.NamesTable NamesTable]] table.
  *
  * @param slickService the [[$package$.$name;format="camel"$.slick.SlickService SlickService]] that will be
  *                     injected, ready to be used
  */
@Singleton
class NameRepository @Inject()(slickService: SlickService)
    extends SlickDatabase(slickService) with NamesTable {

  import jdbcProfileApi._

  def create(name: Name): Future[Name] =
    slickService.run(names.insertOrUpdate(name)).map(id => name.copy(Some(id)))

  def findAll: Future[Seq[Name]] = slickService.run(names.result)

  def findByName(name: String): Future[Option[Name]] = {
    val query: Query[Names, Name, Seq] = names.filter(_.name === name)
    slickService.run(query.result.headOption)
  }

  def update(name: Name): Future[Name] = {
    val query: Query[Names, Name, Seq] = names.filter(_.id === name.id)
    slickService
      .run(query.update(name))
      .map(id => name.copy(id = Some(id)))
  }

  def delete(name: Name): Future[Boolean] =
    slickService.run(names.filter(_.id === name.id).delete) map {
      _ > 0
    }

  def deleteAll(): Future[Boolean] = slickService.run(names.delete) map {
    _ > 0
  }
}
