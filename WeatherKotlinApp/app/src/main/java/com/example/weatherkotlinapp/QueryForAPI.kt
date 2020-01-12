package com.example.weatherkotlinapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QueryForAPI {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon:String,
        @Query("appid") app_id: String
    ): Call<WeatherResponse>
    @GET("data/2.5/forecast/daily?")
    fun getWeatherForWeek(
        @Query("lat")lat:String,
        @Query("lon")lon:String,
        @Query("cnt")cnt:String,
        @Query("appid")appid:String
    ):Call<WeatherResponse>
}