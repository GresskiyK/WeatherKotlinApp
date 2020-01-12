package com.example.weatherkotlinapp

import com.google.gson.annotations.SerializedName

class WeatherItem {
    @SerializedName("max_temp")
    var maxTemp: Float = 0.toFloat()
    @SerializedName("min_temp")
    var minTemp: Float = 0.toFloat()
}