package com.example.weatherkotlinapp.WeatherResponse

import com.google.gson.annotations.SerializedName

class IdOfCity {
    @SerializedName("woeid")
    var id: String? = null
}