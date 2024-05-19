package com.example.caloriecounter.home_screen.data.day_calorie_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DayCalorieData::class],
    version = 1
)
abstract class DayCalorieDb: RoomDatabase() {

    abstract fun dayCalorieDao(): DayCalorieDao
}