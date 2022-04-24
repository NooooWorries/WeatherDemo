package com.czxbnb.weatherdemo.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.czxbnb.weatherdemo.util.assertWeatherResponseEqual
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

    @Test
    fun testInsertWeatherResponseAndGetWeatherResponseById() = runTest {
        // Given - Insert a task
        val weatherResponse = applicationContext.getSampleWeatherResponse(gson)
        weatherDao.insertWeather(weatherResponse)

        // When - Get the weather by id from database
        val loaded = weatherDao.getWeatherById(weatherResponse.id)

        // Then - Check whether the loaded data is what we expected
        assertWeatherResponseEqual(weatherResponse, loaded)
    }

    @Test
    fun testInsertWeatherResponseAndGetWeatherResponseList() = runTest {
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
    fun testInsertWeatherResponseAndDeleteWeatherResponse() = runTest {
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
