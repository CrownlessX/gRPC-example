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

  // Step 1: create a request proto
  val request = getCarRequest {
    name = "cars/123"
  }

  // Step 2: call a stub
  val response = stub.getCar(request)
  // Step 6: handle response
  println(response)

  channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
}
