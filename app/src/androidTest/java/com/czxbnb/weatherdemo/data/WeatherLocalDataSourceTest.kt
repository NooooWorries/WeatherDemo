package com.czxbnb.weatherdemo.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.czxbnb.weatherdemo.WeatherApplication
import com.czxbnb.weatherdemo.database.WeatherDao
import com.czxbnb.weatherdemo.database.WeatherDatabase
import com.czxbnb.weatherdemo.database.WeatherDatabaseTypeConverter
import com.czxbnb.weatherdemo.util.assertWeatherResponseEqual
import com.czxbnb.weatherdemo.util.getSampleWeatherResponse
import com.google.gson.Gson
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class WeatherLocalDataSourceTest {
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var applicationContext: Context
    private lateinit var gson: Gson

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)

    @Inject
    lateinit var weatherLocalDataSource: WeatherLocalDataSource

    @Before
    fun init() {
        hiltRule.inject()
        applicationContext = ApplicationProvider.getApplicationContext()
        gson = Gson()
    }

    @Test
    fun testInsertWeather() = runTest {
        val weatherResponse = applicationContext.getSampleWeatherResponse(gson)
        weatherLocalDataSource.insertWeather(weatherResponse)
        val loaded = weatherLocalDataSource.getWeatherById(weatherResponse.id)
        assertWeatherResponseEqual(weatherResponse, loaded)
    }
}
