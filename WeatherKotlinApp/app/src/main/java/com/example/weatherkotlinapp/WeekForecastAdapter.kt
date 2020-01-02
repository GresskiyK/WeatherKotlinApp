package com.example.weatherkotlinapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class WeekForecastAdapter(private val listofItems: List<MyItem>) : RecyclerView.Adapter<WeekForecastAdapter.ViewHolder>() {


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
        private val title: TextView = itemView.findViewById(R.id.dayOfCard)
        private val body:TextView=itemView.findViewById(R.id.degreesOfCard)
        fun bindItems(items: MyItem) {
            title.text = items.firstName
            body.text=items.lastName+"\u00B0"
        }

    }
}