package com.example.weatherkotlinapp.WeatherResponse

import com.google.gson.annotations.SerializedName

class Weather {
    @SerializedName("description")
    var description: String? = null
    @SerializedName("main")
    var mainName: String? = null
}