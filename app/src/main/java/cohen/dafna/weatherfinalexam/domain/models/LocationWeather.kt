package cohen.dafna.weatherfinalexam.domain.models

class LocationWeather(
    val icon: String,
    val lat: Double,
    val lng: Double,
    val summary: String,
    val temperature: Double,
    val time: Long
)