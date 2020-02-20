package com.example.retrofitexamplekotlin

import android.provider.ContactsContract
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface DataTypeDAO {
    @Query("SELECT * from dataType")
    fun getAll(): List<DataType>

    @Insert(onConflict = REPLACE)
    fun insert(weatherData: DataType)
}