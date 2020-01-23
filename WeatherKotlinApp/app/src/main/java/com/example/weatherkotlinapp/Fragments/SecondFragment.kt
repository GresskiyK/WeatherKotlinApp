package com.example.weatherkotlinapp.Fragments

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.weatherkotlinapp.Callbacks
import com.example.weatherkotlinapp.Queries.QueriesForApi
import com.example.weatherkotlinapp.Queries.QueryForAPI
import com.example.weatherkotlinapp.R
import com.example.weatherkotlinapp.RetrofitClass
import com.example.weatherkotlinapp.WeatherResponse.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt



class SecondFragment : Fragment(){
    private lateinit var geocoder: Geocoder
    private lateinit var textViewTitle: TextView
    private lateinit var textViewDegrees: TextView
    private lateinit var textViewHumidity: TextView
    private lateinit var textViewWind: TextView
    private lateinit var textViewWeatherDescription: TextView
    private lateinit var textViewTimeOfDay: TextView
    private lateinit var constraintLayoutSecondFragment: ConstraintLayout
    private lateinit var window: Window


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
        textViewTimeOfDay=view.findViewById(R.id.textViewTimeOfDay)
        constraintLayoutSecondFragment=view.findViewById(R.id.constraintOfSecondFragment)
        window= activity!!.window
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        getCity(
            lat.toDouble(),
            lon.toDouble())
        QueriesForApi().getData(getCallback())
        setTimeDescription()
        return view
    }
    companion object {
        var retrofit= RetrofitClass.getRetrofit()
        var AppId = "b6907d289e10d714a6e88b30761fae22"
        var lat:String=""
        var lon:String=""
    }

    private fun setTimeDescription(){
        val data= SimpleDateFormat("HH", Locale.getDefault())
        when (data.format(Date()).toInt()) {
            in 23..3 -> textViewTimeOfDay.text="NIGHT"
            in 4..11 -> textViewTimeOfDay.text="MORNING"
            in 12..16 -> textViewTimeOfDay.text="AFTERNOON"
            in 17..22 -> textViewTimeOfDay.text="EVENING"
        }
    }

    private fun getCity(lat:Double,lon:Double){
        geocoder= Geocoder(context, Locale.getDefault())
        val address=geocoder.getFromLocation(lat,lon,1)
        val city= address[0].locality
        textViewTitle.text=city
    }

    private fun getCallback():Callbacks {
        return object : Callbacks{
            override fun onComplete(description:String,mainName:String,humidity:String,degrees:String,wind:String) {
                textViewWeatherDescription.text=description
                textViewDegrees.text=degrees
                textViewHumidity.text=humidity
                textViewWind.text=wind
                when(mainName){
                        "Clouds"->constraintLayoutSecondFragment.setBackgroundResource(R.drawable.clouds_gradient)
                        "Clear"->constraintLayoutSecondFragment.setBackgroundResource(R.drawable.clear_gradient)
                        "Mist"-> constraintLayoutSecondFragment.setBackgroundResource(R.drawable.clouds_gradient)
                        "Fog"->constraintLayoutSecondFragment.setBackgroundResource(R.drawable.clouds_gradient)
                        "Rain"->constraintLayoutSecondFragment.setBackgroundResource(R.drawable.rain_gradient)
                        else->constraintLayoutSecondFragment.setBackgroundResource(R.drawable.gradient)
                }
            }
        }
    }
}