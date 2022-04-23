package com.czxbnb.weatherdemo.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.czxbnb.weatherdemo.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@ProvidedTypeConverter
class WeatherDatabaseTypeConverter(private val gson: Gson) {
    @TypeConverter
    fun cloudsToString(clouds: Clouds?): String? = gson.toJson(clouds)

    @TypeConverter
    fun stringToClouds(cloudsString: String?): Clouds? =
        gson.fromJson(cloudsString, Clouds::class.java)

    @TypeConverter
    fun coordToString(coord: Coord?): String? = gson.toJson(coord)

    @TypeConverter
    fun stringToCoord(coordString: String?): Coord = gson.fromJson(coordString, Coord::class.java)

    @TypeConverter
    fun mainToString(main: Main?): String? = gson.toJson(main)

    @TypeConverter
    fun stringToMain(mainString: String?): Main? = gson.fromJson(mainString, Main::class.java)

    @TypeConverter
    fun sysToString(sys: Sys?): String? = gson.toJson(sys)

    @TypeConverter
    fun stringToSys(sysString: String?): Sys? = gson.fromJson(sysString, Sys::class.java)

    @TypeConverter
    fun weatherListToString(weatherList: List<Weather>?): String? = gson.toJson(weatherList)

    @TypeConverter
    fun stringToWeatherList(weatherListString: String?): List<Weather>? {
        val listType: Type = object : TypeToken<List<Weather?>?>() {}.type
        return gson.fromJson(weatherListString, listType)
    }

    @TypeConverter
    fun windToString(wind: Wind?): String? = gson.toJson(wind)

    @TypeConverter
    fun stringToWind(windString: String?): Wind? = gson.fromJson(windString, Wind::class.java)
}
