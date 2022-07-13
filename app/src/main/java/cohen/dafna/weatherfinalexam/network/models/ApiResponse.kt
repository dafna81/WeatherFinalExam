package cohen.dafna.weatherfinalexam.network.models

data class ApiWrapper(
    val data: ApiLocationWeather,
    val message: String
)

data class ApiLocationWeather(
    val apparentTemperature: Double,
    val cloudCover: Int,
    val dewPoint: Double,
    val humidity: Double,
    val icon: String,
    val lat: Double,
    val lng: Double,
    val ozone: Double,
    val precipIntensity: Int,
    val pressure: Double,
    val summary: String,
    val temperature: Double,
    val time: Long,
    val uvIndex: Double,
    val visibility: Double,
    val windBearing: Double,
    val windGust: Double,
    val windSpeed: Double
)