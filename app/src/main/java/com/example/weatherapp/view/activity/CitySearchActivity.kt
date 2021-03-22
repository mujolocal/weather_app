package com.example.weatherapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityCitySearchBinding
import com.example.weatherapp.util.Constants.CITY_NAME
import com.example.weatherapp.util.Constants.WEATHER_LIST
import com.example.weatherapp.util.openActivity
import com.example.weatherapp.util.showLoadingDialog
import com.example.weatherapp.util.viewModel
import com.example.weatherapp.viewmodel.citysearch.CitySearchViewModel

class CitySearchActivity : AppCompatActivity() {
    private val viewModel by viewModel {
        CitySearchViewModel(this.application)
    }

    private val dialog: AlertDialog by showLoadingDialog {
        cancelable = false
        setMessage("Loading data...")
        setRetryVisibility()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCitySearchBinding= DataBindingUtil.setContentView(
            this,
            R.layout.activity_city_search
        )

        setSupportActionBar(binding.toolbar)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.progressBarVisibility.observe(this, Observer { aBoolean ->
            if (aBoolean!!) {
                dialog.show()
            } else
                dialog.dismiss()
        })

        viewModel.cityName.observe(this) {
            viewModel.progressBarVisibility.value = false
            viewModel.liveDataCitySearchError.value = ""
        }

        viewModel.weatherListLiveData.observe(this){
            if(!it.isNullOrEmpty()){
                openActivity(WeatherListActivity::class.java) {
                    putParcelableArrayList(WEATHER_LIST, it)
                    putString(CITY_NAME, viewModel.cityName.value)
                }

            }
        }
    }
}