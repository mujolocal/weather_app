package com.example.weatherapp.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityItemListBinding
import com.example.weatherapp.models.WeatherDetailsDTO
import com.example.weatherapp.util.Constants.CITY_NAME
import com.example.weatherapp.util.Constants.SELECTED_WEATHER_DATA
import com.example.weatherapp.util.Constants.WEATHER_LIST
import com.example.weatherapp.util.openActivity
import com.example.weatherapp.util.replaceFragment
import com.example.weatherapp.util.viewModel
import com.example.weatherapp.view.fragment.WeatherDetailFragment
import com.example.weatherapp.viewmodel.weatherlist.WeatherViewModel
import com.example.weatherapp.viewmodel.weatherlist.adapters.WeatherListAdapter

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [WeatherDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class WeatherListActivity : AppCompatActivity(), WeatherListAdapter.IWeatherSelectionAdapter {

    private lateinit var itemListBinding: ActivityItemListBinding
    private val viewModel: WeatherViewModel by viewModel {
        WeatherViewModel(this.application)
    }
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemListBinding = DataBindingUtil.setContentView(this, R.layout.activity_item_list)
        viewModel.adapter.listener = this
        itemListBinding.viewModel = viewModel
        itemListBinding.lifecycleOwner = this

        val toolbar = itemListBinding.toolbar
        viewModel.toolbarTitle = intent.getStringExtra(CITY_NAME)
        toolbar.title = viewModel.toolbarTitle
        setSupportActionBar(toolbar)

        val weatherList:ArrayList<WeatherDetailsDTO> = intent.getParcelableArrayListExtra(WEATHER_LIST)

        if(!weatherList.isNullOrEmpty()) {
            viewModel.data.clear()
            viewModel.data.addAll(weatherList)
            viewModel.adapter.notifyDataSetChanged()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (itemListBinding.itemListContainer.itemDetailContainer != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
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


    override fun itemSelected(model: WeatherDetailsDTO) {
        when {
            mTwoPane ->
                replaceFragment(R.id.item_detail_container, WeatherDetailFragment()) {
                    putParcelable(SELECTED_WEATHER_DATA, model)
                }
            !mTwoPane ->
                openActivity(WeatherDetailActivity::class.java) {
                    putParcelable(SELECTED_WEATHER_DATA, model)
                    putString(CITY_NAME, viewModel.toolbarTitle)
                }
        }
    }
}