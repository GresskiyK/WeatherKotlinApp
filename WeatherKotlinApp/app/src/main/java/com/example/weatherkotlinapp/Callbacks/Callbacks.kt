package com.example.weatherkotlinapp.Callbacks

import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfWeekRecycler
import com.example.weatherkotlinapp.WeatherResponse.WeatherResponse

interface Callbacks {
    fun completeDailyForecast(
        description: String,
        humidity: String,
        degrees: String,
        wind: String
    ) {}
    fun completeWeekForecast(days: ArrayList<ItemOfWeekRecycler>) {}
    fun completeWoeid(id: String) {}
}