package com.example.weatherkotlinapp.Queries

import com.example.weatherkotlinapp.WeatherResponse.IdOfCity
import com.example.weatherkotlinapp.WeatherResponse.WeatherResponse
import com.example.weatherkotlinapp.WeatherResponse.WeatherWeekResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QueryForAPI {
    @GET("data/2.5/weather")
    fun getCurrentWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon:String,
        @Query("appid") app_id: String
    ): Call<WeatherResponse>
    @GET("/api/location/search/?")
    fun getWoeid(
        @Query("lattlong") lattlong: String
    ): Call<List<IdOfCity>>
    @GET("/api/location/{woeid}/")
    fun getWeather(
        @Path("woeid") id:String
    ): Call<WeatherWeekResponse>
}