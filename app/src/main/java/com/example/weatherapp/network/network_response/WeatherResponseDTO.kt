package com.example.weatherapp.network.network_response

import com.example.weatherapp.models.WeatherDetailsDTO

data class WeatherResponseDTO(
        val cod: String?,
        val message: Int?,
        val cnt: Int?,
        val list: List<WeatherDetailsDTO>
)