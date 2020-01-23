package com.example.weatherkotlinapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherkotlinapp.Adapters.CountriesWeatherAdapter
import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfCountriesRecycler
import com.example.weatherkotlinapp.R

class ThirdFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.third_fragment, container, false)
        val items = listOf(
            ItemOfCountriesRecycler("London", "3"),
            ItemOfCountriesRecycler("Berlin", "0"),
            ItemOfCountriesRecycler("Moscow", "2"),
            ItemOfCountriesRecycler(
                "Amsterdam",
                "-3"
            ),
            ItemOfCountriesRecycler("Paris", "-1"),
            ItemOfCountriesRecycler(
                "San Francisco",
                "2"
            )
        )
        val myAdapter = CountriesWeatherAdapter(items)
        val rv=view.findViewById(R.id.rvForCities) as RecyclerView
        rv.layoutManager= LinearLayoutManager(activity)
        rv.adapter=myAdapter

        return view
    }
}