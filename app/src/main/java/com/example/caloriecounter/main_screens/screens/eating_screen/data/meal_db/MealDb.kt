package com.example.caloriecounter.main_screens.screens.eating_screen.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Meal::class],
    version = 1
)
abstract class MealDb: RoomDatabase() {

    abstract fun mealDao(): MealDao
}