package com.czxbnb.weatherdemo.data

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.czxbnb.weatherdemo.model.*
import com.google.gson.Gson

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
    fun weatherToString(weather: Weather?): String? = gson.toJson(weather)

    @TypeConverter
    fun stringToWeather(weatherString: String?): Weather? =
        gson.fromJson(weatherString, Weather::class.java)

    @TypeConverter
    fun windToString(wind: Wind?): String? = gson.toJson(wind)

    @TypeConverter
    fun stringToWind(windString: String?): Wind? = gson.fromJson(windString, Wind::class.java)
}