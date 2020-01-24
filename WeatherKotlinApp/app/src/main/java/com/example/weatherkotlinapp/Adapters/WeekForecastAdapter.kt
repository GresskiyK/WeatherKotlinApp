package com.example.weatherkotlinapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfWeekRecycler
import com.example.weatherkotlinapp.R

internal class WeekForecastAdapter(private val listofItems: List<ItemOfWeekRecycler>) : RecyclerView.Adapter<WeekForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_for_weekrecycler, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listofItems[position])
    }

    override fun getItemCount(): Int {
        return listofItems.size
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayOfWeek: TextView = itemView.findViewById(R.id.dayOfCard)
        private val maxDegrees:TextView=itemView.findViewById(R.id.maxDegrees)
        private val minDegrees:TextView=itemView.findViewById(R.id.minDegrees)
        private val icon:ImageView=itemView.findViewById(R.id.iconOfWeather)
        private val description:TextView=itemView.findViewById(R.id.textViewDescription)
        fun bindItems(items: ItemOfWeekRecycler) {

            when(items.iconType){
                "hc" ->icon.setImageResource(R.drawable.clouds_icon)
                "hr"-> icon.setImageResource(R.drawable.clouds_with_rain_icon)
                "c"->icon.setImageResource(R.drawable.sun_icon)
                "sn"->icon.setImageResource(R.drawable.snow_icon)
                "lc"->icon.setImageResource(R.drawable.light_cloud_icon)
                "s"->icon.setImageResource(R.drawable.showers_icons)
                "lr"->icon.setImageResource(R.drawable.light_rain_icon)
                "t"->icon.setImageResource(R.drawable.lightning_rain_icon)
                "h"->icon.setImageResource(R.drawable.hail_icon)
                "sl"->icon.setImageResource(R.drawable.snow_with_rain_icon)
                else->{
                    icon.setImageResource(R.drawable.default_dots)
                }
            }
            val strs=items.date.split("-")
            description.text=items.description
            dayOfWeek.text = strs[2] +"."+ strs[1]
            minDegrees.text=items.minDegrees

            maxDegrees.text=".."+items.maxDegrees+"\u00B0"
        }

    }
}