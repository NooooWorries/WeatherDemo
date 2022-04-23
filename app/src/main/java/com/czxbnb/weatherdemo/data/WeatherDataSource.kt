package com.czxbnb.weatherdemo.data

import com.czxbnb.weatherdemo.model.WeatherResponse
import com.czxbnb.weatherdemo.network.Result

interface WeatherDataSource {
    suspend fun insertWeather(weatherResponse: WeatherResponse) {}

    suspend fun getWeatherById(id: Int?): WeatherResponse? {
        return null
    }

    suspend fun getWeatherByQuery(queryMessage: String): Result<WeatherResponse>? {
        return null
    }

    suspend fun getWeatherByCoordinate(
        latitude: Double,
        longitude: Double
    ): Result<WeatherResponse>? {
        return null
    }
}
