package com.example.weatherkotlinapp.Queries

import android.location.Location
import android.util.Log
import com.example.weatherkotlinapp.Callbacks
import com.example.weatherkotlinapp.Fragments.FirstFragment
import com.example.weatherkotlinapp.Fragments.SecondFragment
import com.example.weatherkotlinapp.MainScreen
import com.example.weatherkotlinapp.R
import com.example.weatherkotlinapp.WeatherResponse.WeatherResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class QueriesForApi {

    fun getData(callback:Callbacks){
            val queryForData = SecondFragment.retrofit?.create(
                QueryForAPI::class.java)
            val call = queryForData?.getCurrentWeatherData(
                SecondFragment.lat,
                SecondFragment.lon,
                SecondFragment.AppId
            )
            call?.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    val weatherResponse=response.body()
                    callback.onComplete("${weatherResponse?.weather!![0].description?.toUpperCase()}",
                        "${weatherResponse.weather[0].mainName}",
                        "${(weatherResponse.main?.humidity!!).roundToInt()}%",
                        "${(weatherResponse.main?.temp!!).roundToInt()}",
                        " ${weatherResponse.wind?.speed} M/S")
                    Log.i("asd",weatherResponse.weather[0].mainName)
                }
                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.i("error",t.message)
                }
            })
        }
    fun getLocation(view:MainScreen) {
        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                FirstFragment.lattlong = location?.latitude!!.toString() + "," + location.longitude.toString()
                SecondFragment.lat = location.latitude.toString()
                SecondFragment.lon = location.longitude.toString()
                view.setupOfViewPager()
            }
    }
}