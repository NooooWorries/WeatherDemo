package com.czxbnb.weatherdemo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czxbnb.weatherdemo.repository.WeatherRepository
import com.czxbnb.weatherdemo.model.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _weatherResponseLiveData: MutableLiveData<WeatherResponse?> = MutableLiveData()
    val weatherResponseLiveData: LiveData<WeatherResponse?> = _weatherResponseLiveData

    private val _errorMessageLiveData: MutableLiveData<String?> = MutableLiveData()
    val errorMessageLiveData: LiveData<String?> = _errorMessageLiveData

    fun getWeatherByQuery(queryString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getWeatherByQuery(queryString)
                _weatherResponseLiveData.postValue(response)
            } catch (e: Exception) {
                _errorMessageLiveData.postValue(e.message)
            }
        }
    }

    fun getWeatherByCoordinate(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getWeatherByCoordinate(latitude, longitude)
                _weatherResponseLiveData.postValue(response)
            } catch (e: Exception) {
                _errorMessageLiveData.postValue(e.message)
            }
        }
    }
}
