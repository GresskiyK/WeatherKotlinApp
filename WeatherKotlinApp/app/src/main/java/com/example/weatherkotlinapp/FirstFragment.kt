package com.example.weatherkotlinapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.first_fragment.*

class FirstFragment : Fragment() {

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
        return view
    }
    companion object{
        var items= listOf(
            ItemOfWeekRecycler("1", "3"),
            ItemOfWeekRecycler("3", "0"),
            ItemOfWeekRecycler("2", "2"),
            ItemOfWeekRecycler("3", "-3"),
            ItemOfWeekRecycler("4", "-1"),
            ItemOfWeekRecycler("1", "2")
        )

    }

}