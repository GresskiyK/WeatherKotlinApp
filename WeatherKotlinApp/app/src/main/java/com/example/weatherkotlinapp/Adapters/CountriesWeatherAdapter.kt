package com.example.weatherkotlinapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfCountriesRecycler
import com.example.weatherkotlinapp.R

internal class CountriesWeatherAdapter(private val listofItemOfCountriesRecyclers: List<ItemOfCountriesRecycler>) : RecyclerView.Adapter<CountriesWeatherAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_for_citiesrecycler, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listofItemOfCountriesRecyclers[position])
    }

    override fun getItemCount(): Int {
        return listofItemOfCountriesRecyclers.size
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.dayOfCard)
        private val body: TextView =itemView.findViewById(R.id.degreesOfCard)
        fun bindItems(items: ItemOfCountriesRecycler) {
            title.text = items.firstName
            body.text=items.lastName+"\u00B0"
        }

    }
}