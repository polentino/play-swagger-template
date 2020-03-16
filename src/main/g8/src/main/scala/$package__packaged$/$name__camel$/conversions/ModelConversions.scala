package $package$.$name;format="camel"$.conversions

import $package$.$name;format="camel"$.model.generated.NameResponse
import $package$.$name;format="camel"$.persistence.Name
import play.api.libs.json._

/**
  * Models conversion utility object
  */
object ModelConversions {

  implicit val integerFormat: Format[Integer] = new Format[Integer] {
    override def writes(o: Integer): JsValue = Json.toJson(o.intValue())

    override def reads(json: JsValue): JsResult[Integer] = json match {
      case JsNumber(value) => JsSuccess(value.intValue())
      case _ => JsError("invalid format")
    }
  }

  implicit val nameModelFormat: Format[NameResponse] = Json.format[NameResponse]

  implicit val nameWrites: Writes[Name] = (o: Name) =>
    Json.toJson(NameResponse(o.name, o.frequency))
}
