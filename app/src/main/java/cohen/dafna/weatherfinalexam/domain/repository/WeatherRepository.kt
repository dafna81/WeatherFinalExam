package cohen.dafna.weatherfinalexam.domain.repository

import android.util.Log
import cohen.dafna.weatherfinalexam.data.dao.LocationWeatherDao
import cohen.dafna.weatherfinalexam.data.entity.DbLocationWeather
import cohen.dafna.weatherfinalexam.data.mapper.DbMapper
import cohen.dafna.weatherfinalexam.domain.models.LocationWeather
import cohen.dafna.weatherfinalexam.network.RemoteApiImpl
import cohen.dafna.weatherfinalexam.network.client.ApiClient
import cohen.dafna.weatherfinalexam.network.mapper.ApiMapper
import cohen.dafna.weatherfinalexam.network.models.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

const val TAG = "WeatherRepository"

class WeatherRepository @Inject constructor(
    private val remoteApiImpl: RemoteApiImpl,
    private val apiMapper: ApiMapper,
    private val dbMapper: DbMapper,
    private val locationWeatherDao: LocationWeatherDao
) {
    suspend fun getSearchResultStream(lat: Double, lng: Double): Flow<LocationWeather?> {
        val result = remoteApiImpl.getLocationWeather(lat, lng)
        var locationWeather: LocationWeather? = null
        if (result is Success) {
            locationWeather = apiMapper.mapApiLocationWeatherToDomain(result.data.data)
        }

        Log.d(TAG, locationWeather.toString())
        return flowOf(locationWeather)
    }

    suspend fun getNewsFromDatabaseFlow(): Flow<LocationWeather> = flowOf(
        dbMapper.mapDbLocationWeatherToDomain(locationWeatherDao.getLocationWeatherInfo())
    )

    suspend fun insertLocationWeather(dbLocationWeather: DbLocationWeather) {
        locationWeatherDao.insertLocationWeather(dbLocationWeather)
    }

    suspend fun deleteTable() {
        locationWeatherDao.deleteTable()
    }


}