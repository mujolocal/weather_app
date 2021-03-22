package com.example.weatherapp.viewmodel.weatherlist

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.weatherapp.models.WeatherDetailsDTO
import com.example.weatherapp.viewmodel.weatherlist.adapters.WeatherListAdapter
import java.util.*

class WeatherViewModel(appApplication: Application): ViewModel() {

    val data: ArrayList<WeatherDetailsDTO> = ArrayList()
    val adapter: WeatherListAdapter = WeatherListAdapter(appApplication, data)

    @Volatile
    var toolbarTitle:String = ""


}
