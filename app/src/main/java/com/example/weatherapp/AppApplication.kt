package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.util.Constants

class AppApplication : Application() {

    fun getBaseURL() = Constants.BASE_URL
}