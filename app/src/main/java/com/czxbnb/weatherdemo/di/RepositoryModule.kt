package com.czxbnb.weatherdemo.di

import com.czxbnb.weatherdemo.data.WeatherLocalDataSource
import com.czxbnb.weatherdemo.data.WeatherRemoteDataSource
import com.czxbnb.weatherdemo.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    fun provideWeatherRepository(
        weatherLocalDataSource: WeatherLocalDataSource,
        weatherRemoteDataSource: WeatherRemoteDataSource
    ): WeatherRepository {
        return WeatherRepository(weatherLocalDataSource, weatherRemoteDataSource)
    }
}
