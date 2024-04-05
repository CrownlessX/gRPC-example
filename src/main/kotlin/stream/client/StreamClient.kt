package stream.client

import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.flow.flow
import stream.StreamServiceGrpcKt
import stream.request

suspend fun main() {
  val channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build()
  val stub = StreamServiceGrpcKt.StreamServiceCoroutineStub(channel)

  val requests = flow {
    for (i in 1..5) {
      emit(request {
        query = "MyQuery $i"
      })
    }
  }

  // Launch a coroutine to collect the response stream
  val responseStream = stub.fetchResponseStream(requests)

  responseStream.collect { response ->
    println(response.result)
  }

  // Keep the main thread alive for demonstration purposes
  Thread.sleep(6000)
}