package cohen.dafna.weatherfinalexam.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cohen.dafna.weatherfinalexam.data.entity.DbLocationWeather

@Dao
interface LocationWeatherDao {
    @Insert
    suspend fun insertLocationWeather(dbLocationWeather: DbLocationWeather)

    @Query("SELECT * FROM location_weather")
    suspend fun getLocationWeatherInfo(): DbLocationWeather

    @Query("DELETE FROM location_weather")
    suspend fun deleteTable()

}