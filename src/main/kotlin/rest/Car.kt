package rest

import kotlinx.serialization.Serializable

/**
 *  Car data class
 */
// Missing KDoc for enums, Intellij issue with not seeing enabled `plugin.serialization`
@Suppress("CustomizableKDocMissingDocumentation", "PLUGIN_IS_NOT_ENABLED")
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
  /**
   * Car color
   */
  @Serializable
  data class Color(
    val interior: ColorType?,
    val exterior: ColorType?,
  ) {
    /**
     *  Color type
     */
    enum class ColorType {
      RED,
      BLUE,
      YELLOW,
      WHITE
    }
  }

  /**
   * Car feature
   */
  @Serializable
  data class Feature(
    val featureType: FeatureType,
    val included: Boolean,
  ) {
    /**
     * Car feature type
     */
    enum class FeatureType {
      ANDROID_AUDIO,
      CAR_PLAY,
      BACK_UP_CAMERA
    }
  }
}
