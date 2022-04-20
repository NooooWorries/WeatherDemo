package com.czxbnb.weatherdemo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.czxbnb.weatherdemo.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_response")
    fun getWeatherList(): List<WeatherResponse>

    @Query("SELECT * FROM weather_response where id = :queryId")
    fun getWeatherById(queryId: String): WeatherResponse

    @Insert
    suspend fun insertWeather(weatherResponse: WeatherResponse)
}
