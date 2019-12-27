package com.example.weatherkotlinapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class MenuAdapter(private val arrayList: ArrayList<MyItem>) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_form, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {






        holder.bindItems(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.titleOfCard)
        private val body:TextView=itemView.findViewById(R.id.bodyOfCard)
        fun bindItems(items: MyItem) {
            title.text = items.firstName
            body.text=items.lastName
        }

    }
}