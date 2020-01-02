package com.example.weatherkotlinapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class SecondFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.second_fragment, container, false)
        val textview: TextView = view.findViewById(R.id.textViewDegrees)
        val textViewHumidity:TextView=view.findViewById(R.id.textViewHumidity)
        textViewHumidity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.humidity_icon,0,0,0)
        val textViewWind:TextView=view.findViewById(R.id.textViewWind)
        textViewWind.setCompoundDrawablesWithIntrinsicBounds(R.drawable.wind_icon,0,0,0)
        textview.text="12"+"\u00B0"
        textview.textSize= 80.0F
        return view
    }
}