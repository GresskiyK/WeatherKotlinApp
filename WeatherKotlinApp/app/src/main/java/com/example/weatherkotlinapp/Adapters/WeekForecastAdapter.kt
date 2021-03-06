package com.example.weatherkotlinapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfWeekRecycler
import com.example.weatherkotlinapp.R
import java.text.SimpleDateFormat
import java.util.*

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
        private val constraintLayout:ConstraintLayout=itemView.findViewById(R.id.constraintLay)
        fun bindItems(items: ItemOfWeekRecycler) {

            when(items.iconType){
                "hc" ->{icon.setImageResource(R.drawable.clouds_icon)
                 constraintLayout.setBackgroundResource(R.drawable.item_clouds)}
                "hr"->{ icon.setImageResource(R.drawable.clouds_with_rain_icon)
                constraintLayout.setBackgroundResource(R.drawable.item_heavy_rain)}
                "c"->{icon.setImageResource(R.drawable.sun_icon)
                constraintLayout.setBackgroundResource(R.drawable.item_clear)}
                "sn"->{icon.setImageResource(R.drawable.snow_icon)
                constraintLayout.setBackgroundResource(R.drawable.item_snow)}
                "lc"->{icon.setImageResource(R.drawable.light_cloud_icon)
                constraintLayout.setBackgroundResource(R.drawable.item_clouds)}
                "s"->{icon.setImageResource(R.drawable.showers_icons)
                constraintLayout.setBackgroundResource(R.drawable.item_heavy_rain)}
                "lr"->{icon.setImageResource(R.drawable.light_rain_icon)
                constraintLayout.setBackgroundResource(R.drawable.item_rain)}
                "t"->{icon.setImageResource(R.drawable.lightning_rain_icon)
                constraintLayout.setBackgroundResource(R.drawable.item_heavy_rain)}
                "h"->{icon.setImageResource(R.drawable.hail_icon)
                constraintLayout.setBackgroundResource(R.drawable.item_snow)}
                "sl"->{icon.setImageResource(R.drawable.snow_with_rain_icon)
                constraintLayout.setBackgroundResource(R.drawable.item_rain)}
                else->{
                    icon.setImageResource(R.drawable.default_dots)
                }
            }
            val date=items.date
            val formatForData = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date)
            val formatForName= SimpleDateFormat("EEEE", Locale.ENGLISH)
            val formatForDate= SimpleDateFormat("dd", Locale.ENGLISH)
            val formatForMonth= SimpleDateFormat("MMM",Locale.ENGLISH)
            var nameOFDay=formatForName.format((formatForData))+", "+formatForMonth.format(formatForData)+" "+formatForDate.format(formatForData)
            description.text=items.description
            dayOfWeek.text = nameOFDay
            minDegrees.text=items.minDegrees
            maxDegrees.text=items.maxDegrees
        }

    }
}