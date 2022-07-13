package cohen.dafna.weatherfinalexam.di

import android.content.Context
import android.location.Geocoder
import cohen.dafna.weatherfinalexam.location.SharedLocationManager
import cohen.dafna.weatherfinalexam.util.imageloader.ImageLoader
import cohen.dafna.weatherfinalexam.util.imageloader.ImageLoaderImpl
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
    @Provides
    @Singleton
    fun provideSharedLocationManager(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope
    ): SharedLocationManager =
        SharedLocationManager(context, coroutineScope)

    @Provides
    @Singleton
    fun provideGeocoder(
        @ApplicationContext context: Context): Geocoder {
        return Geocoder(context)
    }

    @Provides
    @Singleton
    fun providePicasso(): Picasso = Picasso.get()

    @Provides
    @Singleton
    fun provideImageLoader(picasso: Picasso): ImageLoader = ImageLoaderImpl(picasso)

}