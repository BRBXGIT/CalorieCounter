package com.example.caloriecounter.main_screens.data.day_nutrient_data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.caloriecounter.main_screens.data.day_nutrient_data.day_nutrient.DayNutrientAmount
import com.example.caloriecounter.main_screens.data.day_nutrient_data.day_nutrient.DayNutrientsDao
import com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient.Nutrient
import com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient.NutrientsDao

@Database(
    entities = [Nutrient::class, DayNutrientAmount::class],
    version = 1
)
abstract class NutrientDb: RoomDatabase() {

    abstract fun nutrientDao(): NutrientsDao
    abstract fun dayNutrientDao(): DayNutrientsDao
}