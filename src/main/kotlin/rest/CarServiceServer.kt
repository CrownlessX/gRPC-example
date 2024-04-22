package rest

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import rest.Car.Color
import rest.Car.Color.ColorType
import rest.Car.Feature
import rest.Car.Feature.FeatureType

/**
 * * Car Service Client REST implementation
 */
fun main() {
  embeddedServer(Netty, port = 8081, module = Application::module).start(wait = true)
}

/**
 * Modules configuration and routing for a server
 */
fun Application.module() {
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
      call.respond(cars.first { car -> car.id == call.parameters["id"]!!.toInt() })
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