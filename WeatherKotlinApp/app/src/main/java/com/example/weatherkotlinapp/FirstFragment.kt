package com.example.weatherkotlinapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
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
        val items = listOf(
            MyItem("29.12", "3"),
            MyItem("30.12", "0"),
            MyItem("31.12", "2"),
            MyItem("1.01", "-3"),
            MyItem("2.01", "-1"),
            MyItem("3.01", "2")
        )

        val myAdapter = WeekForecastAdapter(items)
        val rvTest=view.findViewById(R.id.rvWeekForecast) as RecyclerView
        rvTest.layoutManager= LinearLayoutManager(activity)
        rvTest.adapter=myAdapter

        return view
    }


}