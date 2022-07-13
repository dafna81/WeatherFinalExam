package cohen.dafna.weatherfinalexam.ui.maps

import android.content.ComponentName
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cohen.dafna.weatherfinalexam.R
import cohen.dafna.weatherfinalexam.databinding.FragmentMapsBinding
import cohen.dafna.weatherfinalexam.services.ForegroundOnlyLocationService
import cohen.dafna.weatherfinalexam.ui.home.HomeViewModel
import cohen.dafna.weatherfinalexam.util.SharedPreferenceUtil
import cohen.dafna.weatherfinalexam.util.imageloader.ImageLoader
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MapsFragment : Fragment() {

    @Inject
    lateinit var geocoder: Geocoder

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: HomeViewModel by viewModels()

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val giniApps = LatLng(32.1601721, 34.807908)
        googleMap.addMarker(MarkerOptions().position(giniApps).title("Marker in Gini-Apps"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(giniApps))

        googleMap.setOnMapClickListener {
            val location = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            val address = location.first().locality ?: location.first().adminArea

            googleMap.addMarker(MarkerOptions().position(it).title(address))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(it))

            binding.detailsCardView.visibility = CardView.VISIBLE
            binding.mapsLocationName.text = address
            viewModel.searchWeather(location.first().latitude, location.first().longitude)
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.searchWeatherFlow.collectLatest { locationWeather ->
                        binding.mapsLocationWeather.text =
                            DecimalFormat("#.#").format(
                                ((locationWeather?.temperature ?: 0.0) - 32) * 5 / 9
                            ) + "C"
                        locationWeather?.icon?.let { it1 -> imageLoader.load("https://assetambee.s3-us-west-2.amazonaws.com/weatherIcons/PNG/${it1}.png", binding.mapsWeatherIcon) }
                    }
                }
            }
        }
    }

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }


}

