package example

import com.jaco.example.car.CarServiceGrpcKt
import com.jaco.example.car.getCarRequest
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.TimeUnit

/**
 * Car Service Client gRPC implementation
 */
suspend fun main() {
  val channel = ManagedChannelBuilder.forAddress("localhost", CAR_PORT).usePlaintext()
    .intercept(LoggingInterceptor()).build()

  val stub = CarServiceGrpcKt.CarServiceCoroutineStub(channel)

  val request = getCarRequest {
    name = "cars/123"
  }

  val response = stub.getCar(request)
  println(response)

  channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
}
