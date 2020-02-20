package com.example.weatherkotlinapp.Callbacks


interface Callbacks {
    fun completeDailyForecast(
        description: String,
        humidity: String,
        degrees: String,
        wind: String
    ) {}
}