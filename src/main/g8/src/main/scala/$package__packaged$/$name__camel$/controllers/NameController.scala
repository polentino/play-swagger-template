package $package$.$name;format="camel"$.controllers

import com.typesafe.scalalogging.LazyLogging
import javax.inject.{Inject, Singleton}
import org.apache.commons.lang3.StringUtils
import $package$.$name;format="camel"$.filters.RateLimiterFilter
import $package$.$name;format="camel"$.model.generated.NameResponse
import $package$.$name;format="camel"$.persistence.Name
import $package$.$name;format="camel"$.repositories.NameRepository
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Simple controller that keeps track of how many times we send a '''GET''' request with query parameter
  * `name`, and returns its count.
  *
  * @param nameRepository
  * @param controllerComponents
  */
@Singleton
class NameController @Inject()(
    nameRepository: NameRepository,
    controllerComponents: ControllerComponents
  ) extends AbstractController(controllerComponents) with LazyLogging {

  import $package$.$name;format="camel"$.conversions.ModelConversions._

  private val rateLimiterFilter = new RateLimiterFilter(1)

  def count(name: Option[String] = None): Action[AnyContent] =
    Action.andThen(rateLimiterFilter).async {
    name match {
      case Some(realName) if !StringUtils.isBlank(realName) =>
        nameRepository.findByName(realName) flatMap {
          case Some(user) => nameRepository.update(user.copy(frequency = user.frequency + 1))
          case _ => nameRepository.create(Name(None, realName, 1))
        } map { result =>
          Ok(Json.toJson(NameResponse(result.name, result.frequency)))
        }
      case _ => Future.successful(Ok("Hello, Anonymous"))
    }
  }
}
