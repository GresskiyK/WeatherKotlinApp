package com.example.weatherkotlinapp.Queries

import android.location.Location
import android.util.Log
import com.example.weatherkotlinapp.Callbacks.Callbacks
import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfWeekRecycler
import com.example.weatherkotlinapp.MainScreen
import com.example.weatherkotlinapp.Retrofit.RetrofitClass
import com.example.weatherkotlinapp.WeatherResponse.IdOfCity
import com.example.weatherkotlinapp.WeatherResponse.WeatherResponse
import com.example.weatherkotlinapp.WeatherResponse.WeatherWeekResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.math.roundToInt

class QueriesForApi {
    companion object {
        var lat = ""
        var lon = ""
        var items = ArrayList<ItemOfWeekRecycler>()
        var cityId = ""
        var AppId = "b6907d289e10d714a6e88b30761fae22"
        var lattlong: String = ""
        var flag=true
    }

    fun getData(callback: Callbacks) {
        val retrofit= RetrofitClass().retrofitForDaily()
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
                val weatherResponse=response.body()
                callback.completeDailyForecast(weatherResponse)
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("errormess", t.message)
            }

        })
    }

    fun getLocation(view: MainScreen) {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(view)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                lattlong = location?.latitude!!.toString() + "," + location.longitude.toString()
                lat = location.latitude.toString()
                lon = location.longitude.toString()
                view.setupOfViewPager()
            }
    }

    fun getWoeidOfCity(callback: Callbacks) {
        val retrofit = RetrofitClass().retrofitForWoeid()
        val service = retrofit.create(QueryForAPI::class.java)
        val call = service.getWoeid(lattlong)
        call.enqueue(object : Callback<List<IdOfCity>> {
            override fun onResponse(
                call: Call<List<IdOfCity>>,
                response: Response<List<IdOfCity>>
            ) {
                val weatherResponse = response.body()
                cityId = weatherResponse?.get(0)?.id.toString()
                callback.completeWoeid(cityId)
            }

            override fun onFailure(call: Call<List<IdOfCity>>, t: Throwable) {
                Log.i("errormess", t.message)
            }
        })
    }

    fun getFiveDaysWeather(callback: Callbacks, id: String) {
        val retrofit = RetrofitClass().retrofitForWeek()
        val service = retrofit.create(QueryForAPI::class.java)
        val call = service.getWeather(id)
        call.enqueue(object : Callback<WeatherWeekResponse> {
            override fun onResponse(
                call: Call<WeatherWeekResponse>,
                response: Response<WeatherWeekResponse>
            ) {
                val weatherResponse = response.body()
                if(flag&& weatherResponse!=null) {

                    for (i in 1 until (weatherResponse.weatherList.size)) {
                        items.add(
                            ItemOfWeekRecycler(
                                weatherResponse.weatherList[i].iconType.toString(),
                                weatherResponse.weatherList[i].date.toString(),
                                (weatherResponse.weatherList[i].minTemp.roundToInt()).toString(),
                                (weatherResponse.weatherList[i].maxTemp.roundToInt()).toString(),
                                weatherResponse.weatherList[i].description.toString()
                            )
                        )
                    }
                }
                flag=false
                callback.completeWeekForecast(items)
            }

            override fun onFailure(call: Call<WeatherWeekResponse>, t: Throwable) {
                Log.i("errormess", t.message)
            }
        })
    }
}
