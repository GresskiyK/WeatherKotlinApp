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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.first_fragment, container, false)
        val myAdapter = WeekForecastAdapter(items)
        val rvTest=view.findViewById(R.id.rvWeekForecast) as RecyclerView
        rvTest.layoutManager= LinearLayoutManager(activity)
        rvTest.adapter=myAdapter
        //getWoeidOfCity()
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
//                var list=weatherResponse?.weatherList
//                for(i in 0..list?.size!!){
//                    items.add(ItemOfWeekRecycler(list[i].minTemp.roundToInt().toString(),list[i].maxTemp.roundToInt().toString()))
//                }
                if(flag){
                    items.add(ItemOfWeekRecycler(weatherResponse?.weatherList?.get(1)?.iconType.toString(), weatherResponse?.weatherList?.get(1)?.date.toString(),(weatherResponse?.weatherList?.get(1)?.minTemp?.roundToInt()).toString(),(weatherResponse?.weatherList?.get(1)?.maxTemp?.roundToInt()).toString()))
                    items.add(ItemOfWeekRecycler(weatherResponse?.weatherList?.get(2)?.iconType.toString(),weatherResponse?.weatherList?.get(2)?.date.toString(),(weatherResponse?.weatherList?.get(2)?.minTemp?.roundToInt()).toString(),(weatherResponse?.weatherList?.get(2)?.maxTemp?.roundToInt()).toString()))
                    items.add(ItemOfWeekRecycler(weatherResponse?.weatherList?.get(3)?.iconType.toString(),weatherResponse?.weatherList?.get(3)?.date.toString(),(weatherResponse?.weatherList?.get(3)?.minTemp?.roundToInt()).toString(),(weatherResponse?.weatherList?.get(3)?.maxTemp?.roundToInt()).toString()))
                    items.add(ItemOfWeekRecycler(weatherResponse?.weatherList?.get(4)?.iconType.toString(),weatherResponse?.weatherList?.get(4)?.date.toString(),(weatherResponse?.weatherList?.get(4)?.minTemp?.roundToInt()).toString(),(weatherResponse?.weatherList?.get(4)?.maxTemp?.roundToInt()).toString()))
                    items.add(ItemOfWeekRecycler(weatherResponse?.weatherList?.get(5)?.iconType.toString(),weatherResponse?.weatherList?.get(5)?.date.toString(),(weatherResponse?.weatherList?.get(5)?.minTemp?.roundToInt()).toString(),(weatherResponse?.weatherList?.get(5)?.maxTemp?.roundToInt()).toString()))
                    items
                    flag=false
                }
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
