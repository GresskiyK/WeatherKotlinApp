package com.example.weatherkotlinapp

interface Callbacks {
    fun onComplete(description:String,mainName:String,humidity:String,degrees:String,wind:String)
}