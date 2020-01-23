package com.example.weatherkotlinapp.WeatherResponse

import com.google.gson.annotations.SerializedName

class WeatherResponse {
    @SerializedName("weather")
    var weather=ArrayList<Weather>()
    @SerializedName("main")
    var main: Main? = null
    @SerializedName("wind")
    var wind: Wind? = null
}