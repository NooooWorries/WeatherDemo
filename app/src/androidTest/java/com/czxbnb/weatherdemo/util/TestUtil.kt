package com.czxbnb.weatherdemo.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.czxbnb.weatherdemo.R
import com.czxbnb.weatherdemo.model.WeatherResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun Context.getSampleWeatherResponse(gson: Gson): WeatherResponse {
    val inputStream = resources.openRawResource(R.raw.weather_response)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val itemType = object : TypeToken<WeatherResponse>() {}.type
    return gson.fromJson(reader, itemType)
}

fun assertWeatherResponseEqual(original: WeatherResponse?, target: WeatherResponse?) {
    MatcherAssert.assertThat(original?.id, CoreMatchers.`is`(target?.id))
    MatcherAssert.assertThat(original?.base, CoreMatchers.`is`(target?.base))
    MatcherAssert.assertThat(original?.clouds, CoreMatchers.`is`(target?.clouds))
    MatcherAssert.assertThat(original?.cod, CoreMatchers.`is`(target?.cod))
    MatcherAssert.assertThat(original?.dt, CoreMatchers.`is`(target?.dt))
    MatcherAssert.assertThat(original?.main, CoreMatchers.`is`(target?.main))
    MatcherAssert.assertThat(original?.name, CoreMatchers.`is`(target?.name))
    MatcherAssert.assertThat(original?.sys, CoreMatchers.`is`(target?.sys))
    MatcherAssert.assertThat(original?.timezone, CoreMatchers.`is`(target?.timezone))
    MatcherAssert.assertThat(original?.visibility, CoreMatchers.`is`(target?.visibility))
    MatcherAssert.assertThat(original?.weather, CoreMatchers.`is`(target?.weather))
    MatcherAssert.assertThat(original?.wind, CoreMatchers.`is`(target?.wind))
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}
