package com.czxbnb.weatherdemo.data.weather

import com.czxbnb.weatherdemo.api.WeatherService
import com.czxbnb.weatherdemo.model.WeatherResponse
import com.czxbnb.weatherdemo.network.Result
import com.czxbnb.weatherdemo.util.API_KEY
import java.lang.Exception
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(private val service: WeatherService) {
    suspend fun getWeatherByQuery(queryMessage: String): Result<WeatherResponse> {
        return try {
            val response = service.getWeatherByQuery(queryMessage, API_KEY)
            Result.Success(response)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}
