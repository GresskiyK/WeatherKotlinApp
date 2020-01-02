package com.example.weatherkotlinapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ThirdFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.third_fragment, container, false)
        val items = listOf(
            MyItem("London", "3"),
            MyItem("Berlin", "0"),
            MyItem("Moscow", "2"),
            MyItem("Amsterdam", "-3"),
            MyItem("Paris", "-1"),
            MyItem("San Francisco", "2")
        )

        val myAdapter = CountriesWeatherAdapter(items)
        val rvTest=view.findViewById(R.id.rvForCities) as RecyclerView
        rvTest.layoutManager= LinearLayoutManager(activity)
        rvTest.adapter=myAdapter

        return view
    }
}