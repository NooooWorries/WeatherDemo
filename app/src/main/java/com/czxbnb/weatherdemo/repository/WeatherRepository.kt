package com.czxbnb.weatherdemo.repository

import com.czxbnb.weatherdemo.data.weather.WeatherLocalDataSource
import com.czxbnb.weatherdemo.data.weather.WeatherRemoteDataSource
import com.czxbnb.weatherdemo.model.WeatherResponse
import com.czxbnb.weatherdemo.network.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherLocalDataSource: WeatherLocalDataSource,
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) {
    suspend fun getWeatherByQuery(queryMessage: String): WeatherResponse? {
        return when (val remoteTask = weatherRemoteDataSource.getWeatherByQuery(queryMessage)) {
            is Result.Success -> {
                weatherLocalDataSource.insertWeather(remoteTask.data)
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
