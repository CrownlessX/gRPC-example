package example

import com.jaco.example.car.CarFeedServiceGrpcKt
import com.jaco.example.car.getCarFeedRequest
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.TimeUnit

/**
 * Car Feed Service Client gRPC implementation
 */
suspend fun main() {
  val channel = ManagedChannelBuilder.forAddress("localhost", CAR_PORT).usePlaintext().build()

  val feedStub = CarFeedServiceGrpcKt.CarFeedServiceCoroutineStub(channel)

  feedStub.getCarFeed(getCarFeedRequest {  }).collect {
    println(it)
  }

  channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
}
