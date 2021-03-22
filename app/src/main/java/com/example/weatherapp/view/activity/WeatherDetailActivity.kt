package com.example.weatherapp.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityItemDetailBinding
import com.example.weatherapp.models.WeatherDetailsDTO
import com.example.weatherapp.util.Constants
import com.example.weatherapp.util.Constants.SELECTED_WEATHER_DATA
import com.example.weatherapp.util.addFragment
import com.example.weatherapp.util.viewModel
import com.example.weatherapp.view.fragment.WeatherDetailFragment
import com.example.weatherapp.viewmodel.weatherdetails.WeatherDetailsViewModel

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [WeatherListActivity].
 */
class WeatherDetailActivity : AppCompatActivity() {

    private lateinit var activityItemDetailBinding: ActivityItemDetailBinding
    private val viewModel: WeatherDetailsViewModel by viewModel {
        WeatherDetailsViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityItemDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_item_detail)
        activityItemDetailBinding.apply {
            viewModel = viewModel
            lifecycleOwner = this@WeatherDetailActivity

        }


        val toolbar = activityItemDetailBinding.toolbar
        toolbar.title = intent.getStringExtra(Constants.CITY_NAME)
        setSupportActionBar(toolbar)


        val dataModel = intent.getParcelableExtra<WeatherDetailsDTO>(SELECTED_WEATHER_DATA)
        viewModel.setSelectedWeather(dataModel)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            addFragment(R.id.item_detail_container, WeatherDetailFragment()) {
                putParcelable(SELECTED_WEATHER_DATA, dataModel)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
