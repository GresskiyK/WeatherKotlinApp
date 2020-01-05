package com.example.weatherkotlinapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.graphics.toColorInt
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_for_citiesrecycler.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getData()
    }

    fun continueWithName(view: View) {
        val intent=Intent(this,MainScreen::class.java)
        startActivity(intent)
    }
    companion object {
        var retrofit=RetrofitClass.getRetrofit()
        var AppId = "b6907d289e10d714a6e88b30761fae22"
        var lat="35"
        var lon="139"
    }

    fun getData(){
        val queryForData = retrofit?.create(QueryForAPI::class.java)
        val call = queryForData?.getCurrentWeatherData(lat,lon, AppId)
        call?.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val weatherResponse=response.body()
                textViewStart.text=weatherResponse?.name
                Log.i("tager", "post submitted to API." + (weatherResponse?.name))
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("error",t.message)
            }
        })

    }

}
