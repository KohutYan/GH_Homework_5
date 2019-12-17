package com.kohutyan.gh_homework_5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_row.view.*

class MainAdapter(private val weatherItem: ArrayList<Weather>) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.weather_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return weatherItem.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        notifyDataSetChanged()
        holder.weather.text = weatherItem[position].toString()
    }

}

class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val weather: TextView = view.mainWeather
//    val wind = view.wind
//    val temperature = view.temperature
}