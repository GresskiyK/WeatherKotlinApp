package com.example.weatherkotlinapp.WeatherResponse

import com.google.gson.annotations.SerializedName

class WeatherItem {
    @SerializedName("max_temp")
    var maxTemp: Float = 0.toFloat()
    @SerializedName("min_temp")
    var minTemp: Float = 0.toFloat()
    @SerializedName("applicable_date")
    var date: String? =null
    @SerializedName("weather_state_abbr")
    var iconType:String?=null
    @SerializedName("weather_state_name")
    var description:String?=null
}