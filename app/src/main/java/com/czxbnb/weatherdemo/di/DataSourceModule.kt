package com.czxbnb.weatherdemo.di

import com.czxbnb.weatherdemo.api.WeatherService
import com.czxbnb.weatherdemo.data.WeatherDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {
    @Provides
    fun provideWeatherDataSource(weatherService: WeatherService): WeatherDataSource {
        return WeatherDataSource(weatherService)
    }
}
