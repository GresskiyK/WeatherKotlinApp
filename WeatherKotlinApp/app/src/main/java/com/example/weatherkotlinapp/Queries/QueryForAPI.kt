package com.example.weatherkotlinapp.Queries

import com.example.weatherkotlinapp.WeatherResponse.IdOfCity
import com.example.weatherkotlinapp.WeatherResponse.WeatherResponse
import com.example.weatherkotlinapp.WeatherResponse.WeatherWeekResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QueryForAPI {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon:String,
        @Query("appid") app_id: String
    ): Call<WeatherResponse>
    @GET("/api/location/search/?")
    suspend fun getWoeid(
        @Query("lattlong") lattlong: String
    ): List<IdOfCity>
    @GET("/api/location/{woeid}/")
    suspend fun getWeather(
        @Path("woeid") id:String
    ): WeatherWeekResponse
}