package com.czxbnb.weatherdemo.data

import com.czxbnb.weatherdemo.model.WeatherResponse
import com.czxbnb.weatherdemo.network.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherDataSource: WeatherDataSource,
    private val weatherDao: WeatherDao
) {
    suspend fun getWeatherByQuery(queryMessage: String): WeatherResponse? {
        return when (val remoteTask = weatherDataSource.getWeatherByQuery(queryMessage)) {
            is Result.Success -> {
                weatherDao.insertWeather(remoteTask.data)
                remoteTask.data
            }
            is Result.Error -> {
                throw remoteTask.exception
            }
            else -> {
                null
            }
        }
    }
}
