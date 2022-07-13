package cohen.dafna.weatherfinalexam.network.mapper

import android.content.Context
import android.location.Geocoder
import cohen.dafna.weatherfinalexam.domain.models.LocationWeather
import cohen.dafna.weatherfinalexam.network.models.ApiLocationWeather
import cohen.dafna.weatherfinalexam.network.models.ApiWrapper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApiMapperImpl @Inject constructor() : ApiMapper {
    override fun mapApiWrapperToApiLocationWeather(apiWrapper: ApiWrapper) =
        with(apiWrapper.data) {
            ApiLocationWeather(
                apparentTemperature,
                cloudCover,
                dewPoint,
                humidity,
                icon,
                lat,
                lng,
                ozone,
                precipIntensity,
                pressure,
                summary,
                temperature,
                time,
                uvIndex,
                visibility,
                windBearing,
                windGust,
                windSpeed
            )
        }

    override fun mapApiLocationWeatherToDomain(apiLocationWeather: ApiLocationWeather) =
        with(apiLocationWeather) {
            LocationWeather(icon, lat, lng, summary, temperature, time)
        }
}