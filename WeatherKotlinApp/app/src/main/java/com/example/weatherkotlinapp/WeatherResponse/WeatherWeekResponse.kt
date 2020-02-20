package com.example.weatherkotlinapp.WeatherResponse

import com.google.gson.annotations.SerializedName

class WeatherWeekResponse {
    @SerializedName("consolidated_weather")
    var weatherList= listOf<WeatherItem>()
}