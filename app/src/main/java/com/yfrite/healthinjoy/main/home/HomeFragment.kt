package com.yfrite.healthinjoy.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker.checkSelfPermission
import com.google.android.gms.location.LocationListener
import com.yfrite.healthinjoy.data.UserRepository
import com.yfrite.healthinjoy.databinding.FragmentHomeBinding
import com.yfrite.healthinjoy.util.network.WeatherAPI
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.Nullable
import androidx.navigation.Navigation
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@DelicateCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var userRepository: UserRepository

    private var lat: String = ""
    private var lon: String = ""

    private var mLocation: Location? = null
    private var mLocationManager: LocationManager? = null

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var glide: RequestBuilder<Bitmap>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCards()
        setWeather()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        userRepository = UserRepository(
            requireActivity().getSharedPreferences(
                "user",
                AppCompatActivity.MODE_PRIVATE
            )
        )

        binding.name.text = getTime() + userRepository.name

        return binding.root.rootView
    }

    private fun initCards() {
        binding.cardIntellectualTraining.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToNavIntellectualTraining()
            Navigation.findNavController(it).navigate(action)
        }

        binding.cardPhysicalTraining.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToNavPhysicalTraining()
            Navigation.findNavController(it).navigate(action)
        }
    }

    //Weather
    private fun getWeather(): ArrayList<String> {
        return WeatherAPI.getWeather(
            lat,
            lon,
            "minutely,daily,hourly,alerts",
            "ru",
            "metric"
        )
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    private fun setWeather() {

        GlobalScope.launch {
            mLocationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if (checkAndAskForLocationPermissions()) {
                if (checkGpsEnabled()) {
                    requestLocationUpdates()

                    lon = mLocation?.longitude.toString()
                    lat = mLocation?.latitude.toString()
                    Log.e("location", "$lat $lon")

                    val weather = getWeather()
                    val temperatureStr = weather[0]
                    val descriptionStr = weather[1]
                    val feelsLikeStr = weather[2]
                    val icon = weather[3]

                    MainScope().launch {
                        glide
                            .load(icon)
                            .into(binding.weatherIcon)

                        binding.temperature.text = "${temperatureStr}°"
                        binding.feelsLike.text = "Ощущается как ${feelsLikeStr}°"
                        binding.description.text = descriptionStr

                        binding.onGPS.visibility = View.GONE
                        binding.loading.visibility = View.GONE
                        binding.shimmerWeather.hideShimmer()
                    }

                } else {
                    MainScope().launch {
                        binding.loading.text = "GPS выключен"
                        binding.shimmerWeather.hideShimmer()
                        binding.onGPS.visibility = View.VISIBLE
                        binding.onGPS.setOnClickListener {
                            resultLauncher.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))

                        }
                    }
                }
            }
        }
    }

    //Contracts

    private val permissionResultLauncher = registerForActivityResult(
        RequestPermission()) { result ->
        if (result) {
            if(checkGpsEnabled())
                requestLocationUpdates()
        } else {
            checkAndAskForLocationPermissions()
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == 0) {
            binding.onGPS.visibility = View.GONE
            binding.loading.text = "Загрузка"
            binding.shimmerWeather.showShimmer(true)
            setWeather()
        }
    }

    //Location
    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation(): Location? {

        val providers = mLocationManager!!.getProviders(false)

        var bestLocation: Location? = null
        for (provider in providers) {
            val l = mLocationManager!!.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                // Found best last known location: %s", l);
                bestLocation = l
            }
        }
        return bestLocation
    }

    //current user Location
    private var mLocationListener: LocationListener =
        LocationListener { location -> //Check if the location is not null
            if (location.latitude != 0.0 && location.longitude != 0.0) {
                mLocation = location
            }
        }

    //Start receiving the location updates
    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {

        val provider = LocationManager.NETWORK_PROVIDER

        //Add the location listener and request updated
        mLocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        MainScope().launch {

            mLocationManager?.requestLocationUpdates(
                provider,
                0,
                0.0f,
                mLocationListener::onLocationChanged
            )
        }


        var location = getLastKnownLocation()

        while (location == null) {
            location = getLastKnownLocation()
        }

        Log.e(
            "location",
            "listener: $mLocationListener location: $location provider: $provider"
        )

        mLocationListener.onLocationChanged(location)

    }

    //Current time
    private fun getTime(): String {

        val currentDate = Date()
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        return when(timeFormat.format(currentDate).split(":")[0].toInt()){
            4, 5, 6, 7, 8, 9, 10, 11 -> "Доброе утро, "
            12, 13, 14, 15, 16 -> "Добрый день, "
            17, 18, 19, 20, 21, 22, 23 -> "Добрый вечер, "
            0, 1, 2, 3 -> "Доброй ночи, "
            else -> "time error "
        }
    }

    //Permissions
    @SuppressLint("WrongConstant")
    private fun checkAndAskForLocationPermissions(): Boolean {
        if (checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

            return false
        }
        return true
    }

    private fun checkGpsEnabled(): Boolean {
        val isLocationEnabled = mLocationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)

        return isLocationEnabled!!
    }

}