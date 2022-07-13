package cohen.dafna.weatherfinalexam.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import cohen.dafna.weatherfinalexam.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    private val currentLatitude: MutableStateFlow<Double> = MutableStateFlow(32.1601721)
    private val longitudeFlow = MutableStateFlow(34.807908)

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchWeatherFlow = combine(currentLatitude, longitudeFlow) { lat, lng ->
        LatitudeAndLongitude(lat,lng)
    }.flatMapLatest {
        repository.getSearchResultStream(it.lat, it.lng)
    }

    fun searchWeather(lat: Double, lng: Double) {
        state["lat"] = lat
        currentLatitude.value =
            state.getLiveData("lat", 32.1601721).value!!
        state["lng"] = lng
        longitudeFlow.value =
            state.getLiveData("lng", 34.807908).value!!
    }

    override fun onCleared() {
        super.onCleared()
        state["lat"] = null
        state["lng"] = null
    }

    data class LatitudeAndLongitude (val lat: Double, val lng: Double)

}