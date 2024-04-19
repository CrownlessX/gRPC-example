import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.java.Java
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.logging.*
import rest.Car

suspend fun main() {
  val client = HttpClient(Java) {
    install(ContentNegotiation) {
      json()
    }

    install(Logging)
  }

  val response: HttpResponse = client.get("http://localhost:8080/cars")

  val cars: List<Car> = response.body()
  println("Cars: $cars")
  client.close()
}