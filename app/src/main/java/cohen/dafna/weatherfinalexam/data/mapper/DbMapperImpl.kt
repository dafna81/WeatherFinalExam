package cohen.dafna.weatherfinalexam.data.mapper

import cohen.dafna.weatherfinalexam.data.entity.DbLocationWeather
import cohen.dafna.weatherfinalexam.domain.models.LocationWeather
import javax.inject.Inject

class DbMapperImpl @Inject constructor(): DbMapper {
    override fun mapDbLocationWeatherToDomain(dbLocationWeather: DbLocationWeather): LocationWeather = with(dbLocationWeather) {
        LocationWeather(icon, lat, lng, summary, temperature, time)
    }
}