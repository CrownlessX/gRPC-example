package grpc.server

import helloworld.GreeterGrpcKt
import helloworld.HelloWorld.HelloWorldReply
import helloworld.HelloWorld.HelloWorldRequest
import helloworld.helloWorldRequest
import io.grpc.inprocess.InProcessChannelBuilder
import io.grpc.inprocess.InProcessServerBuilder
import io.grpc.testing.GrpcCleanupRule
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import server.GreeterImpl

class GreeterImplTest {

  @JvmField @Rule
  val grpcCleanup: GrpcCleanupRule = GrpcCleanupRule()

  private lateinit var blockingStub: GreeterGrpcKt.GreeterCoroutineStub

  @BeforeEach
  fun setUp() {
    // Create a server and register your service implementation
    val serverName = InProcessServerBuilder.generateName()
    grpcCleanup.register(
      InProcessServerBuilder
        .forName(serverName)
        .addService(GreeterImpl())
        .build()
        .start()
    )

    // Create a channel and stub for your service
    blockingStub = GreeterGrpcKt.GreeterCoroutineStub(
      grpcCleanup.register(InProcessChannelBuilder.forName(serverName).build())
    )
  }

  @Test
  fun sayHello_returnsGreeting() = runBlocking {
    // Call your service methods and perform assertions
    val request: HelloWorldRequest = helloWorldRequest {
      name = "Test"
    }
    val reply: HelloWorldReply = blockingStub.sayHello(request)

    assertEquals("Hello Test!", reply.message)
  }
}