package com.czxbnb.weatherdemo.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.czxbnb.weatherdemo.model.WeatherResponse
import com.czxbnb.weatherdemo.util.getSampleWeatherResponse
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class WeatherDaoTest {
    private lateinit var applicationContext: Context
    private lateinit var gson: Gson
    private lateinit var database: WeatherDatabase
    private lateinit var weatherDao: WeatherDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        applicationContext = ApplicationProvider.getApplicationContext()
        gson = Gson()
        database = Room.inMemoryDatabaseBuilder(applicationContext, WeatherDatabase::class.java)
            .addTypeConverter(WeatherDatabaseTypeConverter(gson))
            .build()
        weatherDao = database.weatherDao()
    }

    @After
    fun closeDb() = database.close()

    private fun assertWeatherResponseEqual(original: WeatherResponse, target: WeatherResponse) {
        MatcherAssert.assertThat(original.id, `is`(target.id))
        MatcherAssert.assertThat(original.base, `is`(target.base))
        MatcherAssert.assertThat(original.clouds, `is`(target.clouds))
        MatcherAssert.assertThat(original.cod, `is`(target.cod))
        MatcherAssert.assertThat(original.dt, `is`(target.dt))
        MatcherAssert.assertThat(original.main, `is`(target.main))
        MatcherAssert.assertThat(original.name, `is`(target.name))
        MatcherAssert.assertThat(original.sys, `is`(target.sys))
        MatcherAssert.assertThat(original.timezone, `is`(target.timezone))
        MatcherAssert.assertThat(original.visibility, `is`(target.visibility))
        MatcherAssert.assertThat(original.weather, `is`(target.weather))
        MatcherAssert.assertThat(original.wind, `is`(target.wind))
    }

    @Test
    fun insertWeatherResponseAndGetWeatherResponseById() = runTest {
        // Given - Insert a task
        val weatherResponse = applicationContext.getSampleWeatherResponse(gson)
        weatherDao.insertWeather(weatherResponse)

        // When - Get the weather by id from database
        val loaded = weatherDao.getWeatherById(weatherResponse.id)

        // Then - Check whether the loaded data is what we expected
        assertWeatherResponseEqual(weatherResponse, loaded)
    }

    @Test
    fun insertWeatherResponseAndGetWeatherResponseList() = runTest {
        // Given - Insert a task
        val weatherResponse = applicationContext.getSampleWeatherResponse(gson)
        weatherDao.insertWeather(weatherResponse)

        // When - Get the weather list from database
        val loaded = weatherDao.getWeatherList()

        // Then check whether the loaded data is what we expected
        MatcherAssert.assertThat(loaded.size, `is`(1))
        assertWeatherResponseEqual(weatherResponse, loaded[0])
    }

    @Test
    fun insertWeatherResponseAndDeleteWeatherResponse() = runTest {
        // Given - Insert a task
        val weatherResponse = applicationContext.getSampleWeatherResponse(gson)
        weatherDao.insertWeather(weatherResponse)

        // When - Delete the weather from database
        weatherDao.deleteWeather(weatherResponse.id)

        // Then check whether the loaded data is what we expected
        val loaded = weatherDao.getWeatherById(weatherResponse.id)
        MatcherAssert.assertThat(loaded, nullValue())
    }
}
