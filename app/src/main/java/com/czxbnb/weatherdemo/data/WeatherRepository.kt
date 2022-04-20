package com.czxbnb.weatherdemo.data

import android.app.DownloadManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherDataSource: WeatherDataSource,
    private val weatherDao: WeatherDao
) {
    fun getWeatherByQuery(query: String) {

    }
}