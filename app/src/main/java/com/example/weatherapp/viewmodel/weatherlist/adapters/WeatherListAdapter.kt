package com.example.weatherapp.viewmodel.weatherlist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.models.WeatherDetailsDTO
import com.example.weatherapp.util.*
import kotlinx.android.synthetic.main.weather_list_item.view.*
import java.util.*

class WeatherListAdapter(
    private val mContext: Context,
    private val weatherList: ArrayList<WeatherDetailsDTO>
) : RecyclerViewAdapter<WeatherDetailsDTO, WeatherListAdapter.MyViewHolder>() {

    private var selectedPosition = -1
    var listener: IWeatherSelectionAdapter? = null

    override fun updateData(data: List<WeatherDetailsDTO>) {
        if (data.isEmpty()) {
            this.weatherList.clear()
        } else {
            this.weatherList.addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.weather_list_item, parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.loadData(position)

    override fun getItemCount(): Int = weatherList.size


    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun loadData(position: Int) {
            val data = weatherList[position]
            view.apply {
                setBackgroundColor(getBackgroundColor(position))

                weather_list_item.tag = position
                weather_list_item.setOnClickListener {
                    listener?.itemSelected(data)
                    selectedPosition = position
                    notifyDataSetChanged()
                }

                weather_title.text = data.weather?.get(0)?.main ?: ""
                weather_temp.text = String.format(
                    view.context.getString(R.string.temperature_with_degrees),
                    data.main?.temp?.toString() ?: "0"
                )

            }

        }

        private fun getBackgroundColor(position: Int): Int =
            if (selectedPosition == position)
                ContextCompat.getColor(mContext, R.color.colorAccent)
            else ContextCompat.getColor(mContext, android.R.color.white)

    }

    interface IWeatherSelectionAdapter {
        fun itemSelected(model: WeatherDetailsDTO)
    }
}