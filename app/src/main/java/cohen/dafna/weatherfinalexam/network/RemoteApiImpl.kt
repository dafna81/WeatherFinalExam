package cohen.dafna.weatherfinalexam.network

import cohen.dafna.weatherfinalexam.network.client.ApiClient
import cohen.dafna.weatherfinalexam.network.models.ApiWrapper
import cohen.dafna.weatherfinalexam.network.models.Failure
import cohen.dafna.weatherfinalexam.network.models.Result
import cohen.dafna.weatherfinalexam.network.models.Success
import javax.inject.Inject

class RemoteApiImpl @Inject constructor(
    private val apiClient: ApiClient
) : RemoteApi {
    override suspend fun getLocationWeather(lat: Double, lng: Double): Result<ApiWrapper> =
        try {
            Success(apiClient.searchLatestWeatherByLatLng(lat, lng))
        } catch (e: Throwable) {
            Failure(e)
        }

}