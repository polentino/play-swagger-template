package $package$.$name;format="camel"$.repositories

import $package$.$name;format="camel"$.helpers.InMemoryRandomDbName
import $package$.$name;format="camel"$.persistence.Name
import org.scalatest.{AsyncFlatSpec, GivenWhenThen, Matchers}
import play.api.mvc.Results

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, _}

class NameRepositorySpec
    extends AsyncFlatSpec with Results with Matchers with GivenWhenThen with InMemoryRandomDbName {

  private implicit val timeout: Duration = 10 seconds
  private val repository = new NameRepository(service)
  private val benderRodriguez = "BenderRodriguez"
  private val expectedResult = Name(Some(1), benderRodriguez, 1)
  private val expectedUpdatedResult = expectedResult.copy(frequency = 2)

  it should "persist names occurrences properly" in {
    Given("an empty repository")
    Await.result(repository.findAll, timeout) shouldBe empty

    When("a new record 'BenderRodriguez' is added")
    Await.result(repository.create(Name(None, benderRodriguez, 1)), timeout) shouldBe expectedResult
    Then("the repository has size 1")
    val result1 = Await.result(repository.findAll, timeout)
    result1 should have size 1
    And("contains the entry BenderRodriguez")
    result1.head shouldBe expectedResult

    When("record 'BenderRodriguez' is updated")
    Await.result(repository.update(expectedUpdatedResult), timeout) shouldBe expectedUpdatedResult
    Then("the repository has still size 1")
    val result2 = Await.result(repository.findAll, timeout)
    result2 should have size 1

    When("we search for record 'BenderRodriguez'")
    val result3 = Await.result(repository.findByName(benderRodriguez), timeout)
    Then("the result should be defined")
    result3.isDefined shouldBe true
    And("the frequency is 2")
    result3.get shouldBe expectedUpdatedResult

    When("we delete record 'BenderRodriguez'")
    val result4 = Await.result(repository.delete(result3.get), timeout)
    Then("the operation should have been performed correctly")
    result4 shouldBe true
    And("the repository should be empty")
    Await.result(repository.findAll, timeout) shouldBe empty
  }
}
