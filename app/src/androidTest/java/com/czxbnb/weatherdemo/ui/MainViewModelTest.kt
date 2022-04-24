package com.czxbnb.weatherdemo.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.czxbnb.weatherdemo.data.FakeWeatherRemoteDataSource
import com.czxbnb.weatherdemo.data.WeatherLocalDataSource
import com.czxbnb.weatherdemo.repository.WeatherRepository
import com.czxbnb.weatherdemo.util.getOrAwaitValue
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
class MainViewModelTest {
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var weatherLocalDataSource: WeatherLocalDataSource

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)

    @Before
    fun init() {
        hiltRule.inject()
        val weatherRemoteDataSource = FakeWeatherRemoteDataSource(
            mutableListOf(
                ApplicationProvider.getApplicationContext<Context>()
                    .getSampleWeatherResponse(Gson())
            )
        )
        val weatherRepository = WeatherRepository(weatherLocalDataSource, weatherRemoteDataSource)
        viewModel = MainViewModel(weatherRepository)
    }

    @Test
    fun testGetWeatherByQuery() = runTest {
        viewModel.getWeatherByQuery("Mountain View")
        MatcherAssert.assertThat(
            viewModel.weatherResponseLiveData.getOrAwaitValue()?.name,
            `is`("Mountain View")
        )
    }

    @Test
    fun testGetWeatherByCoordinate() = runTest {
        viewModel.getWeatherByCoordinate(37.39, -122.08)
        MatcherAssert.assertThat(
            viewModel.weatherResponseLiveData.getOrAwaitValue()?.name,
            `is`("Mountain View")
        )
    }
}
