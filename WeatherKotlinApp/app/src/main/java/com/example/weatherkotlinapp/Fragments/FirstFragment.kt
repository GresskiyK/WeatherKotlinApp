package com.example.weatherkotlinapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherkotlinapp.Adapters.WeekForecastAdapter
import com.example.weatherkotlinapp.Callbacks.Callbacks
import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfWeekRecycler
import com.example.weatherkotlinapp.ProgressBar
import com.example.weatherkotlinapp.Queries.QueriesForApi
import com.example.weatherkotlinapp.R

class FirstFragment : Fragment() {
    private lateinit var rvTest: RecyclerView
    private lateinit var progressBar: android.widget.ProgressBar
    private lateinit var constraintLayoutFirstFragment: ConstraintLayout



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.first_fragment, container, false)
        rvTest = view.findViewById(R.id.rvWeekForecast) as RecyclerView
        progressBar=view.findViewById(R.id.progressBar)
        constraintLayoutFirstFragment = view.findViewById(R.id.constraintOfFirstFragment)
        ProgressBar.visible(progressBar)
        if(items.isNotEmpty()){
            items.clear()
            QueriesForApi().getWoeidOfCity(callbackForWoeid())
        }
        else{
            QueriesForApi().getWoeidOfCity(callbackForWoeid())
        }

        return view
    }
    private fun dynamicBackGround(mainName:String){
        when (mainName) {
            "Clouds" -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.clouds_gradient)
            "Clear" -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.clear_gradient)
            "Mist" -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.clouds_gradient)
            "Fog" -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.clouds_gradient)
            "Rain" -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.light_rain_gradient)
            "Drizzle"->constraintLayoutFirstFragment.setBackgroundResource(R.drawable.clouds_gradient)
            else -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.gradient)
        }
    }

    private fun callbackForDays(): Callbacks {
        return object : Callbacks {
            override fun completeWeekForecast(days: ArrayList<ItemOfWeekRecycler>) {
                items = days
                setupForRecycler()
                dynamicBackGround(QueriesForApi.mainName)

                ProgressBar.disable(progressBar)
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
