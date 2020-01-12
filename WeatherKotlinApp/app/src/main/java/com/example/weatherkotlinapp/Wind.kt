package com.example.weatherkotlinapp

import com.google.gson.annotations.SerializedName

class Wind {
    @SerializedName("speed")
    var speed: Float = 0.toFloat()
}