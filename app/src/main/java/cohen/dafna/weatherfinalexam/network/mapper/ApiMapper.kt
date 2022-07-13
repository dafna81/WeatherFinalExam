package cohen.dafna.weatherfinalexam.network.mapper

import cohen.dafna.weatherfinalexam.domain.models.LocationWeather
import cohen.dafna.weatherfinalexam.network.models.ApiLocationWeather
import cohen.dafna.weatherfinalexam.network.models.ApiWrapper

interface ApiMapper {
    fun mapApiWrapperToApiLocationWeather(apiWrapper: ApiWrapper): ApiLocationWeather
    fun mapApiLocationWeatherToDomain(apiLocationWeather: ApiLocationWeather): LocationWeather
}