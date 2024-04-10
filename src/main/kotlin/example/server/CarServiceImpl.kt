package example.server

import com.jaco.example.car.CarKt.color
import com.jaco.example.car.CarKt.feature
import com.jaco.example.car.CarServiceGrpcKt
import com.jaco.example.car.CarServiceOuterClass
import com.jaco.example.car.CarServiceOuterClass.Car.Color.ColorType.BLUE
import com.jaco.example.car.CarServiceOuterClass.Car.Color.ColorType.RED
import com.jaco.example.car.CarServiceOuterClass.Car.Color.ColorType.WHITE
import com.jaco.example.car.CarServiceOuterClass.Car.Feature.FeatureType.BACK_UP_CAMERA
import com.jaco.example.car.CarServiceOuterClass.Car.Feature.FeatureType.CAR_PLAY
import com.jaco.example.car.car
import com.jaco.example.car.listCarsResponse

internal class CarServiceImpl : CarServiceGrpcKt.CarServiceCoroutineImplBase() {

  override suspend fun getCar(request: CarServiceOuterClass.GetCarRequest): CarServiceOuterClass.Car =
    when(request.name) {
      highlander.name -> highlander
      corolla.name -> corolla
      else -> car {  }
    }

  override suspend fun listCars(request: CarServiceOuterClass.ListCarsRequest): CarServiceOuterClass.ListCarsResponse =
    listCarsResponse {
      cars += listOf(highlander, corolla)
      // set other fields
    }

  companion object {
    val highlander = car {
      name = "2023 Toyota Highlander XSE"
      model = "Toyota Highlander"
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
      name = "2024 Toyota Corolla LE"
      model = "Toyota Corolla"
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