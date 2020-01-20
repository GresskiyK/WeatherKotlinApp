package com.example.weatherkotlinapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.first_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class FirstFragment : Fragment() {
    var flag:Boolean=true
    private lateinit var rvTest:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.first_fragment, container, false)
        rvTest=view.findViewById(R.id.rvWeekForecast) as RecyclerView
        getWoeidOfCity()
        return view
    }
    private fun getWoeidOfCity() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(QueryForAPI::class.java)
        val call = service.getWoeid(lattlong)
        call.enqueue(object : Callback<List<IdOfCity>> {
            override fun onResponse(call: Call<List<IdOfCity>>, response: Response<List<IdOfCity>>) {
                var weatherResponse:List<IdOfCity> = response.body()!!
                cityId=weatherResponse[0].id.toString()
                getFiveDaysWeather()
                Log.i("tager", "result:" + weatherResponse[0].id)
            }
            override fun onFailure(call: Call<List<IdOfCity>>, t: Throwable) {
                Log.i("error",t.message)
            }
        })

    }
    fun getFiveDaysWeather(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(QueryForAPI::class.java)
        val call = service.getWeather(cityId)
        call.enqueue(object : Callback<WeatherWeekResponse> {
            override fun onResponse(call: Call<WeatherWeekResponse>, response: Response<WeatherWeekResponse>) {
                var weatherResponse=response.body()

                if(flag){
                    for(i in 1..5){
                        items.add(ItemOfWeekRecycler(weatherResponse?.weatherList?.get(i)?.iconType.toString(), weatherResponse?.weatherList?.get(i)?.date.toString(),(weatherResponse?.weatherList?.get(i)?.minTemp?.roundToInt()).toString(),(weatherResponse?.weatherList?.get(i)?.maxTemp?.roundToInt()).toString()))
                    }
                    flag=false
                }
                val myAdapter = WeekForecastAdapter(items)
                rvTest.layoutManager= LinearLayoutManager(activity)
                rvTest.adapter=myAdapter
                Log.i("tager", "result:" + weatherResponse?.weatherList?.get(0)?.date)
            }
            override fun onFailure(call: Call<WeatherWeekResponse>, t: Throwable) {
                Log.i("error",t.message)
            }
        })
    }
    companion object{
        var items= ArrayList<ItemOfWeekRecycler>()
        var lattlong:String=""
        var BaseUrl = "https://www.metaweather.com"
        var cityId=""

    }

}
