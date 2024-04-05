package grpc.server

import io.grpc.ServerBuilder
import server.GreeterImpl

private const val PORT = 55111

/**
 * Small server for [GreeterImpl]
 */
fun main() {
  val server = ServerBuilder.forPort(PORT)
    .addService(GreeterImpl())
    .build()

  server.start()
  println("Server started on port $PORT")
  server.awaitTermination()
}