package com.example.weatherkotlinapp

import com.google.gson.annotations.SerializedName

class WeatherWeekResponse {
    @SerializedName("consolidated_weather")
    var weatherList= ArrayList<WeatherItem>()
}