package com.example.weatherapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentItemDetailBinding
import com.example.weatherapp.util.Constants.SELECTED_WEATHER_DATA
import com.example.weatherapp.util.viewModel
import com.example.weatherapp.viewmodel.weatherdetails.WeatherDetailsViewModel
import kotlinx.android.synthetic.main.activity_item_detail.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class WeatherDetailFragment : Fragment() {
    private var fragmentItemDetailBinding: FragmentItemDetailBinding? = null
    private val viewModel: WeatherDetailsViewModel by viewModel {
        WeatherDetailsViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentItemDetailBinding = FragmentItemDetailBinding.inflate(inflater, container, false)

        activity?.let {
            fragmentItemDetailBinding?.let { binding ->
                binding.viewModel = viewModel
                binding.lifecycleOwner = this
            }

            arguments?.let { args ->
                if (args.containsKey(SELECTED_WEATHER_DATA))
                    viewModel.setSelectedWeather( args.getParcelable(SELECTED_WEATHER_DATA))
            }
        }
        return fragmentItemDetailBinding!!.root
    }


}