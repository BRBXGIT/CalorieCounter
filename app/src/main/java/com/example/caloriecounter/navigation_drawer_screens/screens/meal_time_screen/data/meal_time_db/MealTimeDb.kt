package com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen.data.meal_time_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MealTime::class],
    version = 1
)
abstract class MealTimeDb: RoomDatabase() {

    abstract fun mealTimeDao(): MealTimeDao
}