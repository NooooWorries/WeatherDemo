package com.czxbnb.weatherdemo.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.czxbnb.weatherdemo.data.FakeWeatherRemoteDataSource
import com.czxbnb.weatherdemo.data.WeatherLocalDataSource
import com.czxbnb.weatherdemo.util.getSampleWeatherResponse
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class WeatherRepositoryTest {
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var applicationContext: Context
    private lateinit var weatherRepository: WeatherRepository

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
        weatherRepository = WeatherRepository(
            weatherLocalDataSource, FakeWeatherRemoteDataSource(
                mutableListOf(applicationContext.getSampleWeatherResponse(Gson()))
            )
        )
    }

    @Test
    fun testGetWeatherByQuery() = runTest {
        val remoteLoaded = weatherRepository.getWeatherByQuery("Mountain View")
        val localLoaded = weatherLocalDataSource.getWeatherById(remoteLoaded?.id)
        MatcherAssert.assertThat(remoteLoaded?.name, `is`("Mountain View"))
        MatcherAssert.assertThat(localLoaded?.name, `is`("Mountain View"))
    }

    @Test
    fun testGetWeatherByCoordinate() = runTest {
        val remoteLoaded = weatherRepository.getWeatherByCoordinate(37.39, -122.08)
        val localLoaded = weatherLocalDataSource.getWeatherById(remoteLoaded?.id)
        MatcherAssert.assertThat(remoteLoaded?.name, `is`("Mountain View"))
        MatcherAssert.assertThat(localLoaded?.name, `is`("Mountain View"))
    }
}
