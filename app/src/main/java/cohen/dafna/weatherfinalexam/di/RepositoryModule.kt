package cohen.dafna.weatherfinalexam.di

import cohen.dafna.weatherfinalexam.data.mapper.DbMapper
import cohen.dafna.weatherfinalexam.data.mapper.DbMapperImpl
import cohen.dafna.weatherfinalexam.domain.repository.WeatherRepository
import cohen.dafna.weatherfinalexam.network.RemoteApi
import cohen.dafna.weatherfinalexam.network.RemoteApiImpl
import cohen.dafna.weatherfinalexam.network.mapper.ApiMapper
import cohen.dafna.weatherfinalexam.network.mapper.ApiMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRemoteApi(remoteApiImpl: RemoteApiImpl): RemoteApi

    @Binds
    @Singleton
    abstract fun bindApiMapper(apiMapperImpl: ApiMapperImpl): ApiMapper

    @Binds
    @Singleton
    abstract fun bindDbMapper(dbMapperImpl: DbMapperImpl): DbMapper

}