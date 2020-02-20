package com.example.retrofitexamplekotlin

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataType::class], version = 1)
abstract class DataTypeBase : RoomDatabase() {

    abstract fun weatherDataDao(): DataTypeDAO

    companion object {
        private var INSTANCE: DataTypeBase? = null

        fun getInstance(context: Context): DataTypeBase? {
            if (INSTANCE == null) {
                synchronized(DataTypeBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DataTypeBase::class.java, "data.db")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}