package com.czxbnb.weatherdemo.util

import androidx.annotation.DrawableRes
import com.czxbnb.weatherdemo.R
import java.util.*

object WeatherUtil {
    private val WEATHER_STATUS_MAPPER = mapOf(
        Pair("01d", R.drawable.ic_weather_sunny),
        Pair("01n", R.drawable.ic_weather_sunny),
        Pair("02d", R.drawable.ic_weather_mostly_cloudy),
        Pair("02n", R.drawable.ic_weather_mostly_cloudy),
        Pair("03d", R.drawable.ic_weather_cloudy),
        Pair("03n", R.drawable.ic_weather_cloudy),
        Pair("04d", R.drawable.ic_weather_cloudy),
        Pair("04n", R.drawable.ic_weather_cloudy),
        Pair("09d", R.drawable.ic_weather_drizzle),
        Pair("09n", R.drawable.ic_weather_drizzle),
        Pair("10d", R.drawable.ic_weather_drizzle),
        Pair("10n", R.drawable.ic_weather_drizzle),
        Pair("11d", R.drawable.ic_weather_thunderstorms),
        Pair("11n", R.drawable.ic_weather_thunderstorms),
        Pair("13d", R.drawable.ic_weather_snow),
        Pair("13n", R.drawable.ic_weather_snow),
        Pair("50d", R.drawable.ic_weather_cloudy),
        Pair("50n", R.drawable.ic_weather_cloudy),
    )

    @DrawableRes
    fun getWeatherIconIdByState(iconCode: String?): Int =
        WEATHER_STATUS_MAPPER[iconCode?.lowercase()] ?: R.drawable.ic_weather_sunny
}
