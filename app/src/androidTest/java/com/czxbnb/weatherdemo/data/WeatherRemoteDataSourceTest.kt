package com.czxbnb.weatherdemo.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.czxbnb.weatherdemo.model.WeatherResponse
import com.czxbnb.weatherdemo.network.succeeded
import com.czxbnb.weatherdemo.util.getSampleWeatherResponse
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class WeatherRemoteDataSourceTest {
    private lateinit var applicationContext: Context
    private val weatherRemoteDataSource = FakeWeatherRemoteDataSource()

    @Before
    fun init() {
        applicationContext = ApplicationProvider.getApplicationContext()
        weatherRemoteDataSource.weatherResponseList.add(
            applicationContext.getSampleWeatherResponse(Gson())
        )
    }

    @Test
    fun testGetWeatherByQuery() = runTest {
        val response = weatherRemoteDataSource.getWeatherByQuery("Mountain View")
        MatcherAssert.assertThat(response?.succeeded, `is`(true))
    }

    @Test
    fun testGetWeatherByCoordinate() = runTest {
        val response = weatherRemoteDataSource.getWeatherByCoordinate(37.39, -122.08)
        MatcherAssert.assertThat(response?.succeeded, `is`(true))
    }
}
