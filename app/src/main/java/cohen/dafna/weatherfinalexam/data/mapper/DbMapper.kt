package cohen.dafna.weatherfinalexam.data.mapper

import cohen.dafna.weatherfinalexam.data.entity.DbLocationWeather
import cohen.dafna.weatherfinalexam.domain.models.LocationWeather

interface DbMapper {
    fun mapDbLocationWeatherToDomain(dbLocationWeather: DbLocationWeather): LocationWeather
}