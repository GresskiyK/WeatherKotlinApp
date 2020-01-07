package com.example.weatherkotlinapp

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.second_fragment.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt



class SecondFragment : Fragment(){
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var textViewTitle: TextView
    private lateinit var textViewDegrees: TextView
    private lateinit var textViewHumidity: TextView
    private lateinit var textViewWind: TextView
    private lateinit var textViewWeatherDescription: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.second_fragment, container, false)
        textViewDegrees = view.findViewById(R.id.textViewDegrees)
        textViewTitle=view.findViewById(R.id.titleOfSecondFragment)
        textViewWeatherDescription=view.findViewById(R.id.textViewWeatherDescription)
        textViewHumidity=view.findViewById(R.id.textViewHumidity)
        textViewHumidity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.humidity_icon,0,0,0)
        textViewWind=view.findViewById(R.id.textViewWind)
        textViewWind.setCompoundDrawablesWithIntrinsicBounds(R.drawable.wind_icon,0,0,0)
        if(this.activity!=null){
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.activity!!)
        }
        getLocation()
        return view
    }
    companion object {
        var retrofit=RetrofitClass.getRetrofit()
        var AppId = "b6907d289e10d714a6e88b30761fae22"
        var lat:String=""
        var lon:String=""
    }
    @SuppressLint("MissingPermission")
    private fun getLocation(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                lat= location?.latitude!!.toString()
                lon = location.longitude.toString()
                getData()
            }
    }

    private fun getData(){
        val queryForData = retrofit?.create(QueryForAPI::class.java)
        val call = queryForData?.getCurrentWeatherData(lat,lon,AppId)
        call?.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val weatherResponse=response.body()
                textViewTitle.text=weatherResponse?.name?.toUpperCase()
                textViewWeatherDescription.text=weatherResponse?.weather!![0].description?.toUpperCase()
                textViewHumidity.text="${(weatherResponse?.main?.humidity!!).roundToInt()}%"
                textViewDegrees.text= "${(weatherResponse?.main?.temp!!).roundToInt()}°"
                textViewWind.text=" ${weatherResponse?.wind?.speed} K/M"
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("error",t.message)
            }
        })
    }
}