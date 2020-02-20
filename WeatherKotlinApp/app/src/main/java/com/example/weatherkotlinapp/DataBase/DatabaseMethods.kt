package com.example.weatherkotlinapp.DataBase

import com.example.retrofitexamplekotlin.DataType
import com.example.retrofitexamplekotlin.DataTypeBase
import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfWeekRecycler
import com.example.weatherkotlinapp.Queries.QueriesForApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class DatabaseMethods(private var mDb: DataTypeBase? = null) {

    companion object{
        var items = ArrayList<ItemOfWeekRecycler>()
    }


    suspend fun getDataFromDb() : ArrayList<ItemOfWeekRecycler>{
        val weatherData = mDb?.weatherDataDao()?.getAll()
//        if (weatherData == null || weatherData.isEmpty()) {
//            setTextOnMainThread("failed")
//        } else {
        if(weatherData!=null){
            for(i in 1 until (weatherData.size)){
                items.add(
                    ItemOfWeekRecycler(
                        weatherData[i].weather_state_abbr,
                        weatherData[i].applicable_date,
                        (weatherData[i].min_temp.roundToInt()).toString(),
                        (weatherData[i].maxTemp.roundToInt()).toString(),
                        weatherData[i].weather_state_name .toString()
                    )
                )
            }
        }
            //setTextOnMainThread(weatherData[0].applicable_date)
        return items
    }

    fun sendDataInDb(weatherData: DataType) {
        mDb?.weatherDataDao()?.insert(weatherData)
    }
    suspend fun setTextOnMainThread( input:String){
        withContext(Dispatchers.Main){
            //textViewResult.text=input
        }

    }
}