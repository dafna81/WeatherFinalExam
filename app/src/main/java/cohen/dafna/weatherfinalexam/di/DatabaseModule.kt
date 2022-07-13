package cohen.dafna.weatherfinalexam.di

import android.content.Context
import androidx.room.Room
import cohen.dafna.weatherfinalexam.data.DBManager
import cohen.dafna.weatherfinalexam.data.dao.LocationWeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): DBManager =
        Room.databaseBuilder(appContext, DBManager::class.java, "weather_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideLocationWeatherDao(DBManager: DBManager): LocationWeatherDao = DBManager.locationWeatherDao()


}