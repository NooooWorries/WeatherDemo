package com.czxbnb.weatherdemo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.czxbnb.weatherdemo.R
import com.czxbnb.weatherdemo.model.WeatherResponse
import com.czxbnb.weatherdemo.util.WeatherUtil
import com.czxbnb.weatherdemo.util.awaitCurrentLocation
import com.czxbnb.weatherdemo.util.hideKeyboard
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    private lateinit var placeNameTextView: TextView
    private lateinit var iconImageView: ImageView
    private lateinit var weatherTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var temperatureTextView: TextView
    private lateinit var minTextView: TextView
    private lateinit var maxTextView: TextView
    private lateinit var loadingTextView: TextView
    private lateinit var queryEditText: EditText
    private lateinit var weatherLayout: ConstraintLayout
    private lateinit var locateButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize view
        initializeView()

        // Add event for query edit text
        queryEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_GO ||
                actionId == EditorInfo.IME_ACTION_NEXT
            ) {
                hideKeyboard(v)
                viewModel.getWeatherByQuery(v.text.toString())
                showMessage(context?.getString(R.string.loading))
                disableSearch()
            }
            true
        }

        // Add event for locate button
        locateButton.setOnClickListener {
            // Use permissionX to request for fine location permission
            PermissionX.init(activity)
                .permissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                .request { allGranted, _, _ ->
                    if (allGranted) {
                        disableSearch()
                        // If user granted, get precise location
                        showMessage(context?.getString(R.string.loading))
                        lifecycleScope.launch {
                            // Asynchronously obtaining precise location
                            val location =
                                LocationServices.getFusedLocationProviderClient(requireContext())
                                    .awaitCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            if (location?.latitude != null) {
                                viewModel.getWeatherByCoordinate(
                                    location.latitude,
                                    location.longitude
                                )
                            } else {
                                // If location service returns null, show error message
                                showMessage("Unable to get your location")
                                enableSearch()
                            }
                        }
                    } else {
                        // If user denied, show error message
                        // Another request will be made if user click locate button again
                        showMessage("Permission Denied")
                        enableSearch()
                    }
                }
        }

        viewModel.weatherResponseLiveData.observe(viewLifecycleOwner) {
            it?.run { loadDataIntoView(this) }
            hideMessage()
            locateButton.isEnabled = true
            enableSearch()
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            showMessage(context?.getString(R.string.error))
            locateButton.isEnabled = true
            enableSearch()
        }
    }

    private fun initializeView() {
        view?.findViewById<TextView>(R.id.text_view_place_name)?.also { placeNameTextView = it }
        view?.findViewById<ImageView>(R.id.image_view_icon)?.also { iconImageView = it }
        view?.findViewById<TextView>(R.id.text_view_weather)?.also { weatherTextView = it }
        view?.findViewById<TextView>(R.id.text_view_time)?.also { timeTextView = it }
        view?.findViewById<TextView>(R.id.text_view_temperature)?.also { temperatureTextView = it }
        view?.findViewById<TextView>(R.id.text_view_min)?.also { minTextView = it }
        view?.findViewById<TextView>(R.id.text_view_max)?.also { maxTextView = it }
        view?.findViewById<TextView>(R.id.text_view_loading)?.also { loadingTextView = it }
        view?.findViewById<EditText>(R.id.edit_text_query)?.also { queryEditText = it }
        view?.findViewById<ConstraintLayout>(R.id.layout_weather)?.also { weatherLayout = it }
        view?.findViewById<Button>(R.id.button_locate)?.also { locateButton = it }
    }

    private fun loadDataIntoView(weatherResponse: WeatherResponse) {
        placeNameTextView.text = weatherResponse.name
        iconImageView.setImageResource(
            WeatherUtil.getWeatherIconIdByState(
                weatherResponse.weather?.get(
                    0
                )?.icon
            )
        )
        weatherTextView.text = weatherResponse.weather?.get(0)?.main
        timeTextView.text =
            SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).format(System.currentTimeMillis())
        temperatureTextView.text = weatherResponse.main?.temp?.toString()
        minTextView.text = weatherResponse.main?.temp_min?.toString()
        maxTextView.text = weatherResponse.main?.temp_max?.toString()
    }

    private fun showMessage(message: String?) {
        loadingTextView.visibility = View.VISIBLE
        weatherLayout.visibility = View.GONE
        message?.let { loadingTextView.text = it }
    }

    private fun hideMessage() {
        loadingTextView.visibility = View.GONE
        weatherLayout.visibility = View.VISIBLE
    }

    private fun enableSearch() {
        locateButton.isEnabled = true
        queryEditText.isEnabled = true
    }

    private fun disableSearch() {
        locateButton.isEnabled = false
        queryEditText.isEnabled = false
    }
}
