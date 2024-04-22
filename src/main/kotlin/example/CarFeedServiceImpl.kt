package example

import com.google.protobuf.timestamp
import com.jaco.example.car.CarFeedServiceGrpcKt
import com.jaco.example.car.CarFeedServiceOuterClass
import com.jaco.example.car.CarFeedServiceOuterClass.CarFeed
import com.jaco.example.car.CarKt.color
import com.jaco.example.car.CarKt.feature
import com.jaco.example.car.CarProto.Car.Color.ColorType
import com.jaco.example.car.CarProto.Car.Feature.FeatureType
import com.jaco.example.car.car
import com.jaco.example.car.carFeed
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CarFeedServiceImpl : CarFeedServiceGrpcKt.CarFeedServiceCoroutineImplBase() {

  override fun getCarFeed(request: CarFeedServiceOuterClass.GetCarFeedRequest): Flow<CarFeed> {
    return flow {
      while (true) {
        emit(generateRecord())

        delay(Random.nextLong(3_000, 10_000))
      }
    }
  }

  private fun generateRecord(): CarFeed = carFeed {
    car = feedRecords[Random.nextInt(0, feedRecords.size)]
    submissionTimestamp = timestamp {
      val millis = System.currentTimeMillis()
      seconds = millis / 1000
      nanos = (millis % 1000 * 1_000_000).toInt()
    }
  }

  companion object {
    val feedRecords = listOf(
      car {
        name = "carsFeed/321"
        model = "Toyota Highlander XSE"
        year = 2023
        color = color {
          interior = ColorType.WHITE
          exterior = ColorType.BLUE
        }
        features += listOf(feature {
          featureType = FeatureType.CAR_PLAY
          included = true
        }, feature {
          featureType = FeatureType.BACK_UP_CAMERA
          included = false
        })
        price = 40000
      },
      car {
        name = "carsFeed/322"
        model = "Toyota Corolla LE"
        year = 2024
        color = color {
          interior = ColorType.RED
          exterior = ColorType.BLUE
        }
        features += listOf(feature {
          featureType = FeatureType.CAR_PLAY
          included = true
        }, feature {
          featureType = FeatureType.BACK_UP_CAMERA
          included = false
        })
        price = 30000
      },
      car {
        name = "carsFeed/323"
        model = "Kia Carnival"
        year = 2021
        color = color {
          interior = ColorType.RED
          exterior = ColorType.RED
        }
        features += listOf(feature {
          featureType = FeatureType.CAR_PLAY
          included = true
        }, feature {
          featureType = FeatureType.ANDROID_AUDIO
          included = true
        }
        )
        price = 32000
      },
      car {
        name = "carsFeed/324"
        model = "Renault Laguna"
        year = 2015
        color = color {
          interior = ColorType.WHITE
          exterior = ColorType.YELLOW
        }
        features += listOf(feature {
          featureType = FeatureType.CAR_PLAY
          included = true
        }, feature {
          featureType = FeatureType.ANDROID_AUDIO
          included = true
        }, feature {
          featureType = FeatureType.BACK_UP_CAMERA
          included = false
        }
        )
        price = 9000
      },
    )
  }
}




