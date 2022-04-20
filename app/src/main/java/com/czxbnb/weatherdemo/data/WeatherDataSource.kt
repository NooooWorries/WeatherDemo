package com.czxbnb.weatherdemo.data

import com.czxbnb.weatherdemo.api.WeatherService
import com.czxbnb.weatherdemo.model.WeatherResponse
import java.lang.Exception
import com.czxbnb.weatherdemo.network.Result
import com.czxbnb.weatherdemo.util.API_KEY
import javax.inject.Inject

class WeatherDataSource @Inject constructor(private val service: WeatherService) {
    suspend fun getWeatherByQuery(queryMessage: String): Result<WeatherResponse> {
        return try {
            val response = service.getWeatherByQuery(queryMessage, API_KEY)
            Result.Success(response)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}
