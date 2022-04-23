package com.czxbnb.weatherdemo.data

import com.czxbnb.weatherdemo.model.WeatherResponse
import com.czxbnb.weatherdemo.network.Result
import java.lang.Exception

class FakeWeatherRemoteDataSource(var weatherResponseList: MutableList<WeatherResponse> = mutableListOf()) :
    WeatherDataSource {

    override suspend fun getWeatherByQuery(queryMessage: String): Result<WeatherResponse>? {
        return try {
            val response = weatherResponseList.find { it.name == queryMessage }
            if (response != null) {
                Result.Success(response)
            } else {
                Result.Error(Exception("Unable to find data"))
            }
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    override suspend fun getWeatherByCoordinate(
        latitude: Double,
        longitude: Double
    ): Result<WeatherResponse>? {
        return try {
            val response =
                weatherResponseList.find { it.coord?.lat == latitude && it.coord?.lon == longitude }
            if (response != null) {
                Result.Success(response)
            } else {
                Result.Error(Exception("Unable to find data"))
            }
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}
