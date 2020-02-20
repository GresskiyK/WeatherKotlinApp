package com.example.retrofitexamplekotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dataType")
data class DataType(@PrimaryKey(autoGenerate = true) var id: Long?,
               @ColumnInfo(name = "max_temp") var maxTemp: Float,
               @ColumnInfo(name = "min_temp") var min_temp: Float,
               @ColumnInfo(name = "applicable_date") var applicable_date: String,
               @ColumnInfo(name = "weather_state_abbr") var weather_state_abbr: String,
               @ColumnInfo(name = "weather_state_name") var weather_state_name: String)
{
    constructor():this(null,0F, 0F,"","","")
}