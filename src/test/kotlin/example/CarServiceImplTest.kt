package example

import com.jaco.example.car.CarServiceGrpcKt
import com.jaco.example.car.car
import com.jaco.example.car.copy
import com.jaco.example.car.getCarRequest
import com.jaco.example.car.listCarsRequest
import example.CarServiceImpl.Companion.corolla
import example.CarServiceImpl.Companion.highlander
import io.grpc.inprocess.InProcessChannelBuilder
import io.grpc.inprocess.InProcessServerBuilder
import io.grpc.testing.GrpcCleanupRule
import kotlin.test.assertContentEquals
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CarServiceImplTest {

  private val grpcCleanup: GrpcCleanupRule = GrpcCleanupRule()

  private lateinit var carServiceStub: CarServiceGrpcKt.CarServiceCoroutineStub

  @BeforeEach
  fun setUp() {
    // Create a server and register your service implementation
    val serverName = InProcessServerBuilder.generateName()
    grpcCleanup.register(
      InProcessServerBuilder.forName(serverName)
        .addService(CarServiceImpl())
        .build()
        .start()
    )

    carServiceStub = CarServiceGrpcKt.CarServiceCoroutineStub(
      grpcCleanup.register(
        InProcessChannelBuilder.forName(serverName).build()
      )
    )
  }

  @Test
  fun getCar_returnsCar() = runBlocking {
    val getCarRequest = getCarRequest {
      name = highlander.name
    }
    val result = carServiceStub.getCar(getCarRequest)

    assertEquals(result, highlander)
  }

  @Test
  fun getCar_carNotExists_returnsEmptyCar() = runBlocking {
    val getCarRequest = getCarRequest {
      name = "no such car"
    }
    val result = carServiceStub.getCar(getCarRequest)

    assertEquals(result, car { })
  }

  @Test
  fun createCar_newNameAssigned_returnsCarWithNewName() = runBlocking {
    val createCarRequest = corolla.copy { name = "" }

    val result = carServiceStub.createCar(createCarRequest)

    assertFalse(result.name.equals(corolla.name))
    assertNotEquals(result.name, corolla.name)
  }

  @Test
  fun listCars_returnsListOfCars() = runBlocking {
    val listCarsRequest = listCarsRequest {}

    val result = carServiceStub.listCars(listCarsRequest)

    assertContentEquals(result.carsList, listOf(highlander, corolla))
  }
}