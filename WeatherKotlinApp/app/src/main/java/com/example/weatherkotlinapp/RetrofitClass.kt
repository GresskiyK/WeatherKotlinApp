package com.example.weatherkotlinapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClass {

    companion object {
        private lateinit var retrofit: Retrofit
        val BaseUrl:String="https://openweathermap.org/"
        fun getRetrofit(): Retrofit? {
                retrofit = Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit

        }
    }
}