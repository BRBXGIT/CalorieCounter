package com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Meal::class],
    version = 1
)
@TypeConverters(NutrientConverter::class)
abstract class MealDb: RoomDatabase() {

    abstract fun mealDao(): MealDao
}