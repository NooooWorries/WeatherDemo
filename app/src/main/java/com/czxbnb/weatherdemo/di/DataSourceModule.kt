package com.czxbnb.weatherdemo.di

import com.czxbnb.weatherdemo.api.WeatherService
import com.czxbnb.weatherdemo.data.weather.WeatherLocalDataSource
import com.czxbnb.weatherdemo.data.weather.WeatherRemoteDataSource
import com.czxbnb.weatherdemo.database.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {
    @Provides
    fun provideWeatherLocalDataSource(weatherDao: WeatherDao): WeatherLocalDataSource {
        return WeatherLocalDataSource(weatherDao)
    }

    @Provides
    fun provideWeatherRemoteDataSource(weatherService: WeatherService): WeatherRemoteDataSource {
        return WeatherRemoteDataSource(weatherService)
    }
}
