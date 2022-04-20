package com.czxbnb.weatherdemo.data.weather

import com.czxbnb.weatherdemo.database.WeatherDao
import com.czxbnb.weatherdemo.model.WeatherResponse
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(private val dao: WeatherDao) {
    suspend fun insertWeather(weatherResponse: WeatherResponse) {
        dao.insertWeather(weatherResponse)
    }
}
