package com.example.weatherkotlinapp.Callbacks

import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfWeekRecycler

interface Callbacks {
    fun completeDailyForecast(
        description: String,
        mainName: String,
        humidity: String,
        degrees: String,
        wind: String
    ) {
    }

    fun completeNameOfCity(name: String) {}
    fun completeWeekForecast(days: ArrayList<ItemOfWeekRecycler>) {}
    fun completeWoeid(id: String) {}
}