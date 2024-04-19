import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.callloging.*
import rest.Car
import rest.Car.Color
import rest.Car.Color.ColorType
import rest.Car.Feature
import rest.Car.Feature.FeatureType

fun main() {
  embeddedServer(Netty, port = 8080, module = Application::myApplicationModule).start(wait = true)
}

fun Application.myApplicationModule() {
  install(
    CallLogging
  )
  install(ContentNegotiation) {
    json()
  }

  routing {
    get("/cars") {
      call.respond(cars)
    }

   get("/cars/{id}") {
      call.respond(cars.filter { car -> car.id == call.parameters["id"]!!.toInt()})
    }

    post("/cars") {
      val car = call.receive<Car>()
      cars.add(car)
      call.respond(HttpStatusCode.Created, "Car added successfully")
    }
  }
}

private val highlander = Car(
  id = 123,
  model = "Toyota Highlander XSE",
  year = 2023,
  color = Color(
    interior = ColorType.WHITE,
    exterior = ColorType.BLUE
  ),
  features = listOf(
    Feature(
      featureType = FeatureType.CAR_PLAY,
      included = true
    ), Feature(
      featureType = FeatureType.BACK_UP_CAMERA,
      included = false
    )
  ),
  price = 40000
)

private val cars = mutableListOf(highlander)