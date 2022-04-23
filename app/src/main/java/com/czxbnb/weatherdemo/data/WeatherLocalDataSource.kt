package com.czxbnb.weatherdemo.data

import com.czxbnb.weatherdemo.database.WeatherDao
import com.czxbnb.weatherdemo.model.WeatherResponse
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(private val dao: WeatherDao) : WeatherDataSource {
    override suspend fun insertWeather(weatherResponse: WeatherResponse) {
        dao.insertWeather(weatherResponse)
    }

    override suspend fun getWeatherById(id: Int?): WeatherResponse? = dao.getWeatherById(id)
}
