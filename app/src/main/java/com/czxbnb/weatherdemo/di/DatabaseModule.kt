package com.czxbnb.weatherdemo.di

import android.content.Context
import com.czxbnb.weatherdemo.database.WeatherDao
import com.czxbnb.weatherdemo.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return WeatherDatabase.invoke(context)
    }

    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao {
        return weatherDatabase.weatherDao()
    }
}
