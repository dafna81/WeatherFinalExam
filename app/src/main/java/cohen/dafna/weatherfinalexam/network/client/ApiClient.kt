package cohen.dafna.weatherfinalexam.network.client

import cohen.dafna.weatherfinalexam.network.models.ApiWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("weather/latest/by-lat-lng")
    suspend fun searchLatestWeatherByLatLng(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): ApiWrapper

}