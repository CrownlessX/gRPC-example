package grpc.client

import helloworld.GreeterGrpcKt
import helloworld.helloWorldRequest
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.TimeUnit

/**
 * Small client for [GreeterGrpcKt]
 */
suspend fun main() {
  val channel = ManagedChannelBuilder.forAddress("localhost", 55111).usePlaintext().build()
  val stub = GreeterGrpcKt.GreeterCoroutineStub(channel)

  val request = helloWorldRequest {
    name = "World"
  }
  val response = stub.sayHello(request)

  println(response.message)
  channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
}