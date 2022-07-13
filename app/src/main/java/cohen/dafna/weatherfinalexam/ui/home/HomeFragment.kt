package cohen.dafna.weatherfinalexam.ui.home

import android.Manifest
import android.content.*
import android.location.Geocoder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import cohen.dafna.weatherfinalexam.R
import cohen.dafna.weatherfinalexam.databinding.FragmentHomeBinding
import cohen.dafna.weatherfinalexam.domain.models.LocationWeather
import cohen.dafna.weatherfinalexam.location.SharedLocationManager
import cohen.dafna.weatherfinalexam.network.RemoteApi
import cohen.dafna.weatherfinalexam.network.mapper.ApiMapper
import cohen.dafna.weatherfinalexam.network.models.Success
import cohen.dafna.weatherfinalexam.services.ForegroundOnlyLocationService
import cohen.dafna.weatherfinalexam.util.SharedPreferenceUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {
    @Inject
    lateinit var geocoder: Geocoder

    private var foregroundOnlyLocationServiceBound = false

    private lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var sharedLocationManager: SharedLocationManager

    private val isMonitoring
        get() =
            sharedPreferences.getBoolean(SharedPreferenceUtil.KEY_FOREGROUND_ENABLED, false)

    private var foregroundOnlyLocationService: ForegroundOnlyLocationService? = null
        set(value) {
            field = value
            if (isMonitoring) {
                field?.subscribeToLocationUpdates()
            }
        }

    @Inject
    lateinit var apiMapper: ApiMapper

    @Inject
    lateinit var apiImpl: RemoteApi

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val foregroundOnlyServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as ForegroundOnlyLocationService.LocalBinder
            foregroundOnlyLocationService = binder.service
            foregroundOnlyLocationServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            foregroundOnlyLocationService = null
            foregroundOnlyLocationServiceBound = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        sharedPreferences =
            requireContext().getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
            )

        val locationPermissionRequest =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        Log.d(TAG, "fine granted")
                        lifecycleScope.launch { showLocationDetails() }
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        Log.d(TAG, "coarse granted")
                        lifecycleScope.launch { showLocationDetails() }
                    }
                    else -> {
                        Log.d(TAG, "not granted")
                    }
                }
            }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        return binding.root
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun showLocationDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                sharedLocationManager.locationFlow().collectLatest {
                    binding.locationName.text =
                        async(Dispatchers.IO) {
                            geocoder.getFromLocation(it.latitude, it.longitude, 1).first().locality
                        }.await()
                }
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.inflateMenu(R.menu.menu_main)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_maps -> {
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.action_homeFragment_to_mapsFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        val serviceIntent = Intent(requireContext(), ForegroundOnlyLocationService::class.java)
        requireContext().bindService(
            serviceIntent,
            foregroundOnlyServiceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onStop() {
        if (foregroundOnlyLocationServiceBound) {
            requireContext().unbindService(foregroundOnlyServiceConnection)
            foregroundOnlyLocationServiceBound = false
        }
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onStop()
    }

    override fun onSharedPreferenceChanged(
        sharedPreferences: SharedPreferences?,
        key: String?
    ) {
        //
    }
}