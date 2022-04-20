package com.czxbnb.weatherdemo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.czxbnb.weatherdemo.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_response")
    fun getWeatherList(): Flow<List<WeatherResponse>>

    @Insert
    suspend fun insertWeather(weatherResponse: WeatherResponse): Int
}