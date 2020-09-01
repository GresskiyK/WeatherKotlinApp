package com.example.weatherkotlinapp.Queries

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import com.example.weatherkotlinapp.Callbacks.Callbacks
import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfWeekRecycler
import com.example.weatherkotlinapp.MainScreen
import com.example.weatherkotlinapp.Retrofit.RetrofitClass
import com.example.weatherkotlinapp.WeatherResponse.IdOfCity
import com.example.weatherkotlinapp.WeatherResponse.WeatherResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class QueriesForApi {
    companion object {
        var lat = ""
        var lon = ""
        var items = ArrayList<ItemOfWeekRecycler>()
        var citiesNumbers= listOf<IdOfCity>()
        var cityId = ""
        var AppId = "439d4b804bc8187953eb36d2a8c26a02"//ba99355746555ac126cd7b797cdad4e7
        var lattlong = ""
        var city = ""
        var mainName = ""
    }

    fun getDaily(callback: Callbacks) {
        val retrofit = RetrofitClass().retrofitForDaily()
        val queryForData = retrofit.create(
            QueryForAPI::class.java
        )
        val call = queryForData.getCurrentWeatherData(
            lat,
            lon,
            AppId
        )
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    mainName = "${weatherResponse.weather[0].mainName}"
                    callback.completeDailyForecast(
                        "${weatherResponse.weather[0].description?.toUpperCase()}",
                        "${(weatherResponse.main?.humidity!!).roundToInt()}%",
                        "${(weatherResponse.main?.temp!!).roundToInt()}",
                        " ${weatherResponse.wind?.speed} M/S"
                    )
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("errormess", t.message)
            }

        })
    }

    private fun getCity(view: MainScreen) {
        val geocoder = Geocoder(view, Locale.ENGLISH)
        val address =
            geocoder.getFromLocation(lat.toDouble(), lon.toDouble(), 1)
        city = address[0].locality.toUpperCase()
    }

    fun getLocation(view: MainScreen) {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(view)
        if (ActivityCompat.checkSelfPermission(
                view,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    lattlong = location?.latitude!!.toString() + "," + location.longitude.toString()
                    lat = location.latitude.toString()
                    lon = location.longitude.toString()
                    getCity(view)
                    view.setupOfViewPager()
                }
        }

    }



    suspend fun getWoeid():ArrayList<ItemOfWeekRecycler>{
        citiesNumbers=RetrofitClass().retrofitForWoeid().getWoeid(lattlong)
        cityId= citiesNumbers[0].id.toString()
        return getFiveDaysWeather(cityId)
    }
    suspend fun getFiveDaysWeather(id:String):ArrayList<ItemOfWeekRecycler>{
        val response=RetrofitClass().retrofitForWeek().getWeather(id)
        val weatherList = response.weatherList
        for (i in 1 until (weatherList.size)) {
                        items.add(
                            ItemOfWeekRecycler(
                                weatherList[i].iconType.toString(),
                                weatherList[i].date.toString(),
                                (weatherList[i].minTemp.roundToInt()).toString(),
                                (weatherList[i].maxTemp.roundToInt()).toString(),
                                weatherList[i].description.toString()
                            )
                        )
        }
        return items
    }
}

