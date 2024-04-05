package stream.server

import io.grpc.Server
import io.grpc.ServerBuilder

fun main()  {
  val port = 50051 // Or any available port
  val server: Server = ServerBuilder.forPort(port)
    .addService(StreamServiceImpl())
    .build()
    .start()

  println("Server started, listening on $port")
  server.awaitTermination()
}