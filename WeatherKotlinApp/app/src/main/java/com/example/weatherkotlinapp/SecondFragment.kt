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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.second_fragment, container, false)
        val textViewDegrees: TextView = view.findViewById(R.id.textViewDegrees)
        val textViewTitle: TextView=view.findViewById(R.id.titleOfSecondFragment)
        val textViewWeatherDescription:TextView=view.findViewById(R.id.textViewWeatherDescription)
        val textViewHumidity:TextView=view.findViewById(R.id.textViewHumidity)
        textViewHumidity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.humidity_icon,0,0,0)
        val textViewWind:TextView=view.findViewById(R.id.textViewWind)
        textViewWind.setCompoundDrawablesWithIntrinsicBounds(R.drawable.wind_icon,0,0,0)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.activity!!)
        obtieneLocalizacion()
        getData(textViewTitle,textViewWeatherDescription,textViewHumidity,textViewWind,textViewDegrees)
        return view
    }
    companion object {
        var retrofit=RetrofitClass.getRetrofit()
        var AppId = "b6907d289e10d714a6e88b30761fae22"
        var lat:String="53"
        var lon:String="27"
    }
    @SuppressLint("MissingPermission")
    private fun obtieneLocalizacion(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                textViewDegrees.text= location?.latitude!!.toString()
                textViewHumidity.text = location?.longitude.toString()
            }
    }

    fun getData(textViewTitle:TextView,textViewWeatherDescription:TextView,textViewHumidity: TextView,textViewWind: TextView,textViewDegrees: TextView){
        val queryForData = retrofit?.create(QueryForAPI::class.java)
        val call = queryForData?.getCurrentWeatherData(lat,lon,AppId)
        call?.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val weatherResponse=response.body()
                textViewTitle.text=weatherResponse?.name
                textViewWeatherDescription.text=weatherResponse?.weather!![0].description?.toUpperCase()
                //textViewHumidity.text=(weatherResponse?.main?.humidity!!).roundToInt().toString()+"%"
                //textViewDegrees.text= (weatherResponse?.main?.temp!!).roundToInt().toString()+"°"
                textViewWind.text=weatherResponse?.wind?.speed.toString()+"K/M"
                Log.i("tager", "post submitted to API." + (weatherResponse?.name))
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("error",t.message)
            }
        })
    }
}