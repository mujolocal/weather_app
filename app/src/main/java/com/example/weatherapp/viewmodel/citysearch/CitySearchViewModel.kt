package com.example.weatherapp.viewmodel.citysearch

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.models.MainDTO
import com.example.weatherapp.models.WeatherDetailsDTO
import com.example.weatherapp.network.WeatherApiManager
import com.example.weatherapp.network.network_response.WeatherResponseDTO
import com.example.weatherapp.util.RxSingleSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.ArrayList

class CitySearchViewModel(appApplication: Application):ViewModel() {
    private var disposable: CompositeDisposable? = CompositeDisposable()
    private var rxSingleSchedulers: RxSingleSchedulers = RxSingleSchedulers.DEFAULT
    private var apiService: WeatherApiManager = WeatherApiManager(appApplication)
    private val _weatherListLiveData: MutableLiveData<ArrayList<WeatherDetailsDTO>> = MutableLiveData()

    val weatherListLiveData:LiveData<ArrayList<WeatherDetailsDTO>> = _weatherListLiveData
    var cityName: MutableLiveData<String> = MutableLiveData()
    var liveDataCitySearchError: MutableLiveData<String> = MutableLiveData()
    var progressBarVisibility: MutableLiveData<Boolean> = MutableLiveData()

    fun searchCity() {
        if (cityName.value?.isNotBlank() == true) {
            progressBarVisibility.value = true
            disposable?.add(
                apiService.getWeatherList(cityName.value ?: "")
                    .compose(rxSingleSchedulers.applySchedulers())
                    .subscribe(this::onSuccess, this::onFailure)
            )
        } else {
            liveDataCitySearchError.postValue("Enter City Name")
        }
    }

    private fun onSuccess(weatherResponseDTO: WeatherResponseDTO){
        progressBarVisibility.value = false
        weatherResponseDTO.list.map { it.copy(main = convertToFahrenheit(it.main!!)) }
            .also {
                _weatherListLiveData.value = ArrayList(it)
            }
    }
    private fun onFailure(error: Throwable){
        liveDataCitySearchError.postValue(error.message ?: "Failed To Search")
        progressBarVisibility.value = false
    }

    // converts {@link MainDTO} celsius to fahrenheit
    private fun convertToFahrenheit(mainWeatherDto: MainDTO): MainDTO {
        return mainWeatherDto.copy(
            temp = calculateFahrenheit(mainWeatherDto.temp!!),
            feels_like = calculateFahrenheit(mainWeatherDto.feels_like!!)
        )
    }

    private fun calculateFahrenheit(degrees: Double): Double {
        val degreesInFahrenheit = (degrees * 1.8) + 32
        return BigDecimal(degreesInFahrenheit).setScale(0, RoundingMode.DOWN).toDouble()
    }
    override fun onCleared() {
        super.onCleared()
        disposable?.run {
            clear()
            null
        }
    }
}