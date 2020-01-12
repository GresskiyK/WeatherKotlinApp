package com.example.weatherkotlinapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import java.util.Calendar.DATE
import java.util.Calendar.getInstance

internal class WeekForecastAdapter(private val listofItems: List<ItemOfWeekRecycler>) : RecyclerView.Adapter<WeekForecastAdapter.ViewHolder>() {

    private lateinit var calendar:Calendar

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekForecastAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_for_weekrecycler, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: WeekForecastAdapter.ViewHolder, position: Int) {
        holder.bindItems(listofItems[position])
    }

    override fun getItemCount(): Int {
        return listofItems.size
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayOfWeek: TextView = itemView.findViewById(R.id.dayOfCard)
        private val maxDegrees:TextView=itemView.findViewById(R.id.maxDegrees)
        private val minDegrees:TextView=itemView.findViewById(R.id.minDegrees)
        fun bindItems(items: ItemOfWeekRecycler) {
            calendar= getInstance()
            dayOfWeek.text = calendar.get(Calendar.DAY_OF_MONTH).toString()
            minDegrees.text=items.minDegrees
            maxDegrees.text=".."+items.maxDegrees+"\u00B0"
        }

    }
}