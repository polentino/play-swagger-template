package $package$.$name;format="camel"$.persistence

import $package$.$name;format="camel"$.slick.SlickDatabase
import slick.lifted.ProvenShape

/**
  * Entity table definition for [[$package$.$name;format="camel"$.persistence.Name Name]]
  */
trait NamesTable {
  this: SlickDatabase =>

  import jdbcProfileApi._

  protected class Names(tag: Tag) extends Table[Name](tag, "Names") {

    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name: Rep[String] = column[String]("name")

    def frequency: Rep[Int] = column[Int]("frequency")

    def * : ProvenShape[Name] = (id.?, name, frequency).mapTo[Name]
  }

  val names: TableQuery[Names] = TableQuery[Names]
}
