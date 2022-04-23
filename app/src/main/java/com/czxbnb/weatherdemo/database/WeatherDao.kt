package com.czxbnb.weatherdemo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.czxbnb.weatherdemo.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_response")
    suspend fun getWeatherList(): List<WeatherResponse>

    @Query("SELECT * FROM weather_response where id = :id")
    suspend fun getWeatherById(id: Int?): WeatherResponse?

    @Insert
    suspend fun insertWeather(weatherResponse: WeatherResponse)

    @Query("DELETE FROM weather_response WHERE id = :id")
    suspend fun deleteWeather(id: Int)
}
