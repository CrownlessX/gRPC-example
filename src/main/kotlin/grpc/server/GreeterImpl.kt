package server

import helloworld.GreeterGrpcKt
import helloworld.HelloWorld
import helloworld.helloWorldReply

/**
 * Simple implementation of the gRPC service [GreeterGrpcKt]
 */
class GreeterImpl: GreeterGrpcKt.GreeterCoroutineImplBase() {
  override suspend fun sayHello(request: HelloWorld.HelloWorldRequest): HelloWorld.HelloWorldReply =
    helloWorldReply {
      message = "Hello ${request.name}!"
    }
}