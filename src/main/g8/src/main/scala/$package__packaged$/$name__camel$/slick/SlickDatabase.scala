package $package$.$name;format="camel"$.slick

/**
  * Base class for any Repository: it provides access to `db` and `jdbcProfileApi` objects,
  * which are to needed for importing specific profile api and perform DB operations
  */
abstract class SlickDatabase(val service: SlickService) {
  lazy val jdbcProfileApi = service.db.profile.api
}
