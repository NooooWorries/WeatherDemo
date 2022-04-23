package com.czxbnb.weatherdemo.util

import com.czxbnb.weatherdemo.R
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Test

class WeatherUtilTest {
    @Test
    fun testGetWeatherIconIdByState() {
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("01d"), `is`(R.drawable.ic_weather_sunny))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("01n"), `is`(R.drawable.ic_weather_sunny))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("02d"), `is`(R.drawable.ic_weather_mostly_cloudy))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("02n"), `is`(R.drawable.ic_weather_mostly_cloudy))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("03d"), `is`(R.drawable.ic_weather_cloudy))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("03n"), `is`(R.drawable.ic_weather_cloudy))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("04d"), `is`(R.drawable.ic_weather_cloudy))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("04n"), `is`(R.drawable.ic_weather_cloudy))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("09d"), `is`(R.drawable.ic_weather_drizzle))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("09n"), `is`(R.drawable.ic_weather_drizzle))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("10d"), `is`(R.drawable.ic_weather_drizzle))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("10n"), `is`(R.drawable.ic_weather_drizzle))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("11d"), `is`(R.drawable.ic_weather_thunderstorms))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("11n"), `is`(R.drawable.ic_weather_thunderstorms))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("13d"), `is`(R.drawable.ic_weather_snow))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("13n"), `is`(R.drawable.ic_weather_snow))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("50d"), `is`(R.drawable.ic_weather_cloudy))
        MatcherAssert.assertThat(WeatherUtil.getWeatherIconIdByState("50n"), `is`(R.drawable.ic_weather_cloudy))
    }
}
