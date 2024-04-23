package example

import com.jaco.example.car.CarKt.color
import com.jaco.example.car.CarKt.feature
import com.jaco.example.car.CarProto.Car
import com.jaco.example.car.CarProto.Car.Color.ColorType.BLUE
import com.jaco.example.car.CarProto.Car.Color.ColorType.RED
import com.jaco.example.car.CarProto.Car.Color.ColorType.WHITE
import com.jaco.example.car.CarProto.Car.Feature.FeatureType.BACK_UP_CAMERA
import com.jaco.example.car.CarProto.Car.Feature.FeatureType.CAR_PLAY
import com.jaco.example.car.CarServiceGrpcKt
import com.jaco.example.car.GetCarRequest
import com.jaco.example.car.ListCarsRequest
import com.jaco.example.car.ListCarsResponse
import com.jaco.example.car.car
import com.jaco.example.car.copy
import com.jaco.example.car.listCarsResponse
import kotlin.random.Random

internal class CarServiceImpl : CarServiceGrpcKt.CarServiceCoroutineImplBase() {

  private val cars = mutableListOf(highlander, corolla)

  // Steps 3 & 4 & 5: handle request -> prepare response proto -> return it
  override suspend fun getCar(request: GetCarRequest): Car =
    cars.firstOrNull { car -> car.name == request.name } ?: car { }
  // In Java:
  // cars.stream().filter{ car -> car.name == request.name }
  //              .findFirst()
  //              .orElse(Car.getDefaultInstance())

  override suspend fun createCar(request: Car): Car {
    // Id generation
    val carWithName = request.copy { name = "cars/${Random.nextInt(150, 300)}" }
    // In Java:
    // request.toBuilder().setName("cars/${Random.nextInt(150, 300)}").build()

    // Persist a car
    cars.add(carWithName)

    return carWithName
  }

  override suspend fun listCars(request: ListCarsRequest): ListCarsResponse =
    listCarsResponse {
      cars += listOf(highlander, corolla)
      // set other fields
    }

  companion object {
    val highlander = car {
      name = "cars/123"
      model = "Toyota Highlander XSE"
      year = 2023
      color = color {
        interior = WHITE
        exterior = BLUE
      }
      features += listOf(feature {
        featureType = CAR_PLAY
        included = true
      }, feature {
        featureType = BACK_UP_CAMERA
        included = false
      })
      price = 40000
    }

    // In Java:
    // Car.newBuilder()
    //   .setName("cars/123")
    //   .setModel("Toyota Highlander XSE")
    //   .setYear(2023)
    //   .setColor(Car.Color.newBuilder()
    //               .setInterior(WHITE)
    //               .setExterior(BLUE)
    //               .build())
    //   .addAllFeatures(
    //     listOf(
    //       Car.Feature.newBuilder()
    //         .setFeatureType(CAR_PLAY)
    //         .setIncluded(true)
    //         .build(),
    //       Car.Feature.newBuilder()
    //         .setFeatureType(BACK_UP_CAMERA)
    //         .setIncluded(false)
    //         .build()
    //     )
    //   )
    //   .setPrice(40000)
    //   .build()

    val corolla = car {
      name = "cars/124"
      model = "Toyota Corolla LE"
      year = 2024
      color = color {
        interior = RED
        exterior = BLUE
      }
      features += listOf(feature {
        featureType = CAR_PLAY
        included = true
      }, feature {
        featureType = BACK_UP_CAMERA
        included = false
      })
      price = 30000
    }
  }
}