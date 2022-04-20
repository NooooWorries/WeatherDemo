package com.czxbnb.weatherdemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.czxbnb.weatherdemo.model.WeatherResponse
import com.google.gson.Gson

@Database(entities = [WeatherResponse::class], version = 1)
@TypeConverters(WeatherDatabaseTypeConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    companion object {
        private const val WEATHER_DATABASE_NAME = "weather_database"

        @Volatile
        private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, WeatherDatabase::class.java, WEATHER_DATABASE_NAME)
                .addTypeConverter(WeatherDatabaseTypeConverter(Gson()))
                .build()
    }

    abstract fun weatherDao(): WeatherDao
}
