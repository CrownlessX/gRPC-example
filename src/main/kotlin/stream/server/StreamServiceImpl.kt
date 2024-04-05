package stream.server

import kotlin.random.Random
import stream.StreamServiceGrpcKt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import stream.Stream
import stream.response

/**
 * Implementation of [StreamServiceGrpcKt]
 */
class StreamServiceImpl : StreamServiceGrpcKt.StreamServiceCoroutineImplBase() {
  override fun fetchResponseStream(requests: Flow<Stream.Request>): Flow<Stream.Response> = flow {
    // Simulate processing and generating responses
    requests.collect {
      println("Processing $it")
      for (i in 1..Random.nextInt(1, 5)) {
        emit(response { result = "response $i for: ${it.query}" })
      }
      kotlinx.coroutines.delay(1000)
    }
  }
}