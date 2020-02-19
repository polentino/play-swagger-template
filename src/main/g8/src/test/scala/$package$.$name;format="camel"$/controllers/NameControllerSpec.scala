package $package$.$name;format="camel"$.controllers

import org.mockito.Mockito.when
import $package$.$name;format="camel"$.model.generated.NameResponse
import $package$.$name;format="camel"$.persistence.Name
import $package$.$name;format="camel"$.repositories.NameRepository
import org.scalatest.{Assertion, FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar
import play.api.libs.json.Json
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test.{FakeRequest, StubControllerComponentsFactory}

import scala.concurrent.Future

class NameControllerSpec
    extends FlatSpec with MockitoSugar with StubControllerComponentsFactory with Results
    with Matchers {

  import $package$.$name;format="camel"$.conversions.ModelConversions._

  behavior of "count()"

  it should "return 'Hello, Anonymous' when invoked without parameter" in new Fixture {
    callAndTest(None)
  }

  it should "increase the counter for 'BenderRodriguez' three times" in new Fixture {
    when(nameRepository.findByName(benderRodriguez))
      .thenReturn(
        Future.successful(None),
        Future.successful(Some(Name(Some(1), benderRodriguez, 1))),
        Future.successful(Some(Name(Some(1), benderRodriguez, 2)))
      )

    when(nameRepository.create(Name(None, benderRodriguez, 1)))
      .thenReturn(Future.successful(Name(Some(1), benderRodriguez, 1)))
    when(nameRepository.update(Name(Some(1), benderRodriguez, 2)))
      .thenReturn(Future.successful(Name(Some(1), benderRodriguez, 2)))
    when(nameRepository.update(Name(Some(1), benderRodriguez, 3)))
      .thenReturn(Future.successful(Name(Some(1), benderRodriguez, 3)))

    callAndTest(Some(benderRodriguez), 1)
    callAndTest(Some(benderRodriguez), 2)
    callAndTest(Some(benderRodriguez), 3)
  }

  trait Fixture {
    val benderRodriguez = "BenderRodriguez"
    val nameRepository: NameRepository = mock[NameRepository]
    val controller: NameController = new NameController(nameRepository, stubControllerComponents())

    def callAndTest(name: Option[String], times: Int = 0): Assertion = {
      name match {
        case None =>
          contentAsString(controller.count()(FakeRequest())) shouldBe "Hello, Anonymous"
        case Some(verifiedName) =>
          contentAsJson(controller.count(name)(FakeRequest())) shouldBe Json.toJson(
            NameResponse(verifiedName, times)
          )

      }
    }
  }
}
