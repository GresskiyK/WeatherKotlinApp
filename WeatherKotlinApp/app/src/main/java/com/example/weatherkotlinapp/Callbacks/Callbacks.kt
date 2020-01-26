package com.example.weatherkotlinapp.Callbacks

import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfWeekRecycler
import com.example.weatherkotlinapp.WeatherResponse.WeatherResponse

interface Callbacks {
    fun completeDailyForecast(
        description: String,
        mainName: String,
        humidity: String,
        degrees: String,
        wind: String
    ) {}
    fun completeDailyForecast(
        weatherResponse: WeatherResponse?
    ) {}

    fun completeNameOfCity(name: String) {}
    fun completeWeekForecast(days: ArrayList<ItemOfWeekRecycler>) {}
    fun completeWoeid(id: String) {}
}