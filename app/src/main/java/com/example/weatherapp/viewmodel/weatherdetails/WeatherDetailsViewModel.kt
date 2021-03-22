package com.example.weatherapp.viewmodel.weatherdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.models.WeatherDetailsDTO

class WeatherDetailsViewModel : ViewModel() {

    private var _selectedWeatherData: MutableLiveData<WeatherDetailsDTO> = MutableLiveData()
    var selectedWeatherData: LiveData<WeatherDetailsDTO>? = _selectedWeatherData

    fun setSelectedWeather(weatherDetailsDTO: WeatherDetailsDTO?) {
        _selectedWeatherData.value = weatherDetailsDTO
    }
}