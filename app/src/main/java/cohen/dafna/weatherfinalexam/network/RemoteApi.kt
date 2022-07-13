package cohen.dafna.weatherfinalexam.network

import cohen.dafna.weatherfinalexam.network.models.ApiWrapper
import cohen.dafna.weatherfinalexam.network.models.Result

interface RemoteApi {
    suspend fun getLocationWeather(lat: Double, lng: Double): Result<ApiWrapper>
}