package com.czxbnb.weatherdemo.di

import com.czxbnb.weatherdemo.data.WeatherDao
import com.czxbnb.weatherdemo.data.WeatherDataSource
import com.czxbnb.weatherdemo.data.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    fun provideWeatherRepository(
        weatherDataSource: WeatherDataSource,
        weatherDao: WeatherDao
    ): WeatherRepository {
        return WeatherRepository(weatherDataSource, weatherDao)
    }
}
