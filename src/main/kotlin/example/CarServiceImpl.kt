package example

import com.jaco.example.car.CarKt.color
import com.jaco.example.car.CarKt.feature
import com.jaco.example.car.CarOuterClass
import com.jaco.example.car.CarServiceGrpcKt
import com.jaco.example.car.CarServiceOuterClass
import com.jaco.example.car.CarOuterClass.Car.Color.ColorType.BLUE
import com.jaco.example.car.CarOuterClass.Car.Color.ColorType.RED
import com.jaco.example.car.CarOuterClass.Car.Color.ColorType.WHITE
import com.jaco.example.car.CarOuterClass.Car.Feature.FeatureType.BACK_UP_CAMERA
import com.jaco.example.car.CarOuterClass.Car.Feature.FeatureType.CAR_PLAY
import com.jaco.example.car.car
import com.jaco.example.car.copy
import com.jaco.example.car.listCarsResponse
import kotlin.random.Random

internal class CarServiceImpl : CarServiceGrpcKt.CarServiceCoroutineImplBase() {

  private val cars = mutableListOf(highlander, corolla)

  override suspend fun createCar(request: CarOuterClass.Car): CarOuterClass.Car {
    // Fake id generation
    val carWithName = request.copy { name = "cars/${Random.nextInt(150, 300)}" }
    cars.add(carWithName)

    return carWithName
  }

  override suspend fun getCar(request: CarServiceOuterClass.GetCarRequest): CarOuterClass.Car =
    cars.firstOrNull { car -> car.name == request.name } ?: car{}

  override suspend fun listCars(request: CarServiceOuterClass.ListCarsRequest): CarServiceOuterClass.ListCarsResponse =
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