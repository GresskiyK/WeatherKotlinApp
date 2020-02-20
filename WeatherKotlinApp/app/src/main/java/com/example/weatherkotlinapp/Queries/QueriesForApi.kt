package com.example.weatherkotlinapp.Queries

import android.location.Geocoder
import android.location.Location
import android.util.Log
import com.example.retrofitexamplekotlin.DataType
import com.example.weatherkotlinapp.Callbacks.Callbacks
import com.example.weatherkotlinapp.Fragments.FirstFragment
import com.example.weatherkotlinapp.Fragments.SecondFragment
import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfWeekRecycler
import com.example.weatherkotlinapp.MainScreen
import com.example.weatherkotlinapp.Retrofit.RetrofitClass
import com.example.weatherkotlinapp.WeatherResponse.IdOfCity
import com.example.weatherkotlinapp.WeatherResponse.Main
import com.example.weatherkotlinapp.WeatherResponse.WeatherResponse
import com.example.weatherkotlinapp.WeatherResponse.WeatherWeekResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.withContext
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
        var AppId = "b6907d289e10d714a6e88b30761fae22"//ba99355746555ac126cd7b797cdad4e7
        var lattlong = ""
        var city = ""
        var mainName = ""
    }

    fun getData(callback: Callbacks) {
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
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                lattlong = location?.latitude!!.toString() + "," + location.longitude.toString()
                lat = location.latitude.toString()
                lon = location.longitude.toString()
                getCity(view)
                view.setupOfViewPager()
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
//        val dataType = DataType()
//        dataType.maxTemp= weekItems.get(0).maxTemp
//        sendDataInDb(weatherData = dataType)
        return items
    }
}

