package com.example.caloriecounter.main_screens.data.day_calorie_data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DayCalorieData::class],
    version = 1
)
abstract class DayCalorieDb: RoomDatabase() {

    abstract fun dayCalorieDao(): DayCalorieDao
}