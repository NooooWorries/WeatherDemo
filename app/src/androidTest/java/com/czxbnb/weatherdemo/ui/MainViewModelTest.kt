package com.czxbnb.weatherdemo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.czxbnb.weatherdemo.repository.WeatherRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@HiltAndroidTest
class MainViewModelTest {
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var weatherRepository: WeatherRepository

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)

    @Before
    fun init() {
        hiltRule.inject()
        viewModel = MainViewModel(weatherRepository)
    }

    @Test
    fun sample() {

    }
}
