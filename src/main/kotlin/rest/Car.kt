package rest

import kotlinx.serialization.Serializable

@Serializable
data class Car(
  val id: Int,
  val model: String,
  val year: Int,
  val color: Color,
  val features: List<Feature>,
  val price: Long,
  // ... other fields
) {
  @Serializable
  data class Color(
    val interior: ColorType?,
    val exterior: ColorType?
  ) {
    enum class ColorType {
      RED,
      BLUE,
      YELLOW,
      WHITE
    }
  }

  @Serializable
  data class Feature(
    val featureType: FeatureType,
    val included: Boolean
  ) {
    enum class FeatureType {
      ANDROID_AUDIO,
      CAR_PLAY,
      BACK_UP_CAMERA
    }
  }
}
