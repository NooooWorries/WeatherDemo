package com.czxbnb.weatherdemo.api

import com.czxbnb.weatherdemo.model.WeatherResponse
import com.czxbnb.weatherdemo.util.API_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    companion object {
        fun create(): WeatherService {
            val httpLoggingInterceptor = HttpLoggingInterceptor()

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService::class.java)
        }
    }

    @GET("weather")
    suspend fun getWeatherByQuery(
        @Query("q") queryMessage: String,
        @Query("appid") appId: String
    ): WeatherResponse
}
