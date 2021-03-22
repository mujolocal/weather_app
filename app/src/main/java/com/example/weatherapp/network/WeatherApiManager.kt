package com.example.weatherapp.network

import android.app.Application
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.util.Constants.GET_WEATHER
import com.example.weatherapp.network.network_response.WeatherResponseDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

class WeatherApiManager(application: Application) {

    private val apiService: RestService =
        ApiEndPoint(application).retrofitInstance.create(RestService::class.java)

    fun getWeatherList(cityName:String) =
        apiService.getWeather(cityName)

    interface RestService {
        @GET(GET_WEATHER)
        fun getWeather(
            @Query("q") cityName: String,
            @Query("appid") appId: String = BuildConfig.SECRET_KEY,
            @Query("units") metric: String = "metric"
        ): Single<WeatherResponseDTO>
    }
}