import com.google.inject.AbstractModule
import com.google.inject.name.Names
import play.api.{Configuration, Environment}

class Module(environment: Environment, configuration: Configuration) extends AbstractModule {
  override def configure(): Unit = {
    bindConstant()
      .annotatedWith(Names.named("applicationSchema"))
      .to("$name;format="camel"$")
  }
}
