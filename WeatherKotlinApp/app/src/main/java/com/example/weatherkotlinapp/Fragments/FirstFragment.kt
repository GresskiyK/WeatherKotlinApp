package com.example.weatherkotlinapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherkotlinapp.Adapters.WeekForecastAdapter
import com.example.weatherkotlinapp.Callbacks
import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfWeekRecycler
import com.example.weatherkotlinapp.Queries.QueriesForApi
import com.example.weatherkotlinapp.R

class FirstFragment : Fragment() {
    private lateinit var rvTest: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.first_fragment, container, false)
        rvTest = view.findViewById(R.id.rvWeekForecast) as RecyclerView
        QueriesForApi().getWoeidOfCity(callbackForWoeid())
        return view
    }

    private fun callbackForDays(): Callbacks {
        return object : Callbacks {
            override fun completeWeekForecast(days: ArrayList<ItemOfWeekRecycler>) {
                items = days
                setupForRecycler()
            }
        }
    }

    private fun callbackForWoeid(): Callbacks {
        return object : Callbacks {
            override fun completeWoeid(id: String) {
                QueriesForApi().getFiveDaysWeather(callbackForDays(), id)
            }
        }
    }

    fun setupForRecycler() {
        val myAdapter = WeekForecastAdapter(items)
        rvTest.layoutManager = LinearLayoutManager(activity)
        rvTest.adapter = myAdapter
    }

    companion object {
        var items = ArrayList<ItemOfWeekRecycler>()
    }

}
