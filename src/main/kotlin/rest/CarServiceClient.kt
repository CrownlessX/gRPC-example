import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.java.Java
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import rest.Car

/**
 * Car Service Client REST implementation
 */
suspend fun main() {
  val client = HttpClient(Java) {
    install(ContentNegotiation) {
      json()
    }

    install(Logging)
  }

  val response: HttpResponse = client.get("http://localhost:8081/cars/123")

  val car: Car = response.body()
  println("$car")
  client.close()
}