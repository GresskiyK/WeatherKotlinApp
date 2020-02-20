package com.example.weatherkotlinapp.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexamplekotlin.DataTypeBase
import com.example.weatherkotlinapp.Adapters.WeekForecastAdapter
import com.example.weatherkotlinapp.Callbacks.Callbacks
import com.example.weatherkotlinapp.DataBase.DatabaseMethods
import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfWeekRecycler
import com.example.weatherkotlinapp.ProgressBar
import com.example.weatherkotlinapp.Queries.QueriesForApi
import com.example.weatherkotlinapp.Queries.QueriesForApi.Companion.items
import com.example.weatherkotlinapp.R
import com.example.weatherkotlinapp.WeatherResponse.WeatherItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment() {
    private lateinit var rvTest: RecyclerView
    private lateinit var progressBar: android.widget.ProgressBar
    private lateinit var constraintLayoutFirstFragment: ConstraintLayout
    private var mDb: DataTypeBase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.first_fragment, container, false)
        rvTest=view.findViewById(R.id.rvWeekForecast)
        progressBar = view.findViewById(R.id.progressBar)
        constraintLayoutFirstFragment = view.findViewById(R.id.constraintOfFirstFragment)
        ProgressBar.visible(progressBar)
        if(items.isNotEmpty()){
            items.clear()
            sendRequest()
        }
        else{
            sendRequest()
        }
        return view
    }

    fun sendRequest(){
        CoroutineScope(IO).launch {
            val result=QueriesForApi().getWoeid()
            bindDataWithUi(result)
        }
    }
    fun sendRequestToDatabase(){
        CoroutineScope(IO).launch {
            val result=DatabaseMethods().getDataFromDb()
            bindDataWithUi(result)
        }
    }
    fun checkDB(){
        val weatherData = mDb?.weatherDataDao()?.getAll()
        if (weatherData == null || weatherData.isEmpty()) {
            sendRequest()
        }
        else{
            sendRequestToDatabase()
        }
    }
    suspend fun bindDataWithUi(days:ArrayList<ItemOfWeekRecycler>){
        withContext(Main){
            setupForRecycler(days)
            dynamicBackGround(QueriesForApi.mainName)
            ProgressBar.disable(progressBar)
        }
    }

    private fun dynamicBackGround(mainName: String) {
        when (mainName) {
            "Clouds" -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.clouds_gradient)
            "Clear" -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.clear_gradient)
            "Mist" -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.clouds_gradient)
            "Fog" -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.clouds_gradient)
            "Snow" -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.snow_gradient)
            "Rain" -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.light_rain_gradient)
            "Drizzle" -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.clouds_gradient)
            else -> constraintLayoutFirstFragment.setBackgroundResource(R.drawable.gradient)
        }
    }

    fun setupForRecycler(items: ArrayList<ItemOfWeekRecycler>) {
        val myAdapter = WeekForecastAdapter(items)
        rvTest.layoutManager = LinearLayoutManager(this.activity)
        rvTest.adapter = myAdapter
    }

}
