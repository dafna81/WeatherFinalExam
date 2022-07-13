package cohen.dafna.weatherfinalexam.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_weather")
data class DbLocationWeather(
    @PrimaryKey(autoGenerate = false)
    val cityName: String,
    val lat: Double,
    val lng: Double,
    val temperature: Double,
    val icon: String,
    val summary: String,
    val time: Long
)