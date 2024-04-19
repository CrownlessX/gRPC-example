package example

import io.grpc.ServerBuilder

const val CAR_PORT = 1536

/**
 * Server for CarService
 */
fun main() {
  val server = ServerBuilder.forPort(CAR_PORT)
    .addService(CarServiceImpl())
    .addService(CarFeedServiceImpl())
    .intercept(LoggingInterceptor())
    .build()

  server.start()
  println("Car Server started on port $CAR_PORT")
  server.awaitTermination()
}