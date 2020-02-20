package com.example.weatherkotlinapp.Retrofit

import com.example.weatherkotlinapp.Queries.QueryForAPI
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

//    fun retrofitForWoeid(): Retrofit {
//        val baseUrl = "https://www.metaweather.com"
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
    fun retrofitForWoeid(): QueryForAPI {
        return Retrofit.Builder()
            .baseUrl("https://www.metaweather.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(QueryForAPI::class.java)
    }

    fun retrofitForWeek(): QueryForAPI{
        return Retrofit.Builder()
            .baseUrl("https://www.metaweather.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(QueryForAPI::class.java)
    }

}