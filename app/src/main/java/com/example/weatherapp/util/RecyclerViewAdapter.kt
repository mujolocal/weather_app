package com.example.weatherapp.util

import androidx.recyclerview.widget.RecyclerView


abstract class RecyclerViewAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    abstract fun updateData(data: List<T>)
}
