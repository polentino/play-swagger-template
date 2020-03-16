package $package$.$name;format="camel"$.persistence

/**
  * The class to be persisted in DB
  * @param id
  * @param name
  * @param frequency
  */
case class Name(id: Option[Int], name: String, frequency: Int)
