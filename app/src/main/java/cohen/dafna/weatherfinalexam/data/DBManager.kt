package cohen.dafna.weatherfinalexam.data

import androidx.room.Database
import androidx.room.RoomDatabase
import cohen.dafna.weatherfinalexam.data.dao.LocationWeatherDao
import cohen.dafna.weatherfinalexam.data.entity.DbLocationWeather

@Database(entities = [DbLocationWeather::class], version = 1)
abstract class DBManager : RoomDatabase(){
    abstract fun locationWeatherDao(): LocationWeatherDao
}