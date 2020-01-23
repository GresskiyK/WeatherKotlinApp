package com.example.weatherkotlinapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClass {

    fun retrofitForDaily(): Retrofit {
        val baseUrl = "https://openweathermap.org/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun retrofitForWoeid(): Retrofit {
        val baseUrl = "https://www.metaweather.com"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun retrofitForWeek(): Retrofit {
        val baseUrl = "https://www.metaweather.com"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}