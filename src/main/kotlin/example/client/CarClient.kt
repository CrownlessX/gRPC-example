package example.client

import com.jaco.example.car.CarServiceGrpcKt
import com.jaco.example.car.getCarRequest
import com.jaco.example.car.listCarsRequest
import example.server.CAR_PORT
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.TimeUnit

suspend fun main() {
  val channel = ManagedChannelBuilder.forAddress("localhost", CAR_PORT).usePlaintext().build()
  val stub = CarServiceGrpcKt.CarServiceCoroutineStub(channel)

  val request = getCarRequest { name = "2024 Toyota Corolla LE" }
  // val request = getCarRequest { name = "2023 Toyota Highlander XSE" }
  // val request = getCarRequest { name = "2024 Kia EV9" }
  val response = stub.getCar(request)
  // val response = stub.listCars(listCarsRequest {  })

  println(response)
  channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
}
