package com.czxbnb.weatherdemo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czxbnb.weatherdemo.data.WeatherRepository
import com.czxbnb.weatherdemo.model.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _weatherResponseLiveData: MutableLiveData<WeatherResponse?> = MutableLiveData()
    val weatherResponseLiveData: LiveData<WeatherResponse?> = _weatherResponseLiveData

    fun getWeatherByQuery(queryString: String) {
        viewModelScope.launch {
            val response = repository.getWeatherByQuery(queryString)
            _weatherResponseLiveData.postValue(response)
        }
    }
}