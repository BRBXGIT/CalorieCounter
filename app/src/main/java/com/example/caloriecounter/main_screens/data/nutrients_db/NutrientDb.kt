package com.example.caloriecounter.main_screens.data.nutrients_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Nutrient::class],
    version = 1
)
abstract class NutrientDb: RoomDatabase() {

    abstract fun nutrientDao(): NutrientDao
}