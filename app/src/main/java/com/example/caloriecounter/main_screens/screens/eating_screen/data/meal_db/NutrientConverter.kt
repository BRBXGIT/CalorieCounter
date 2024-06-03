package com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db

import androidx.room.TypeConverter
import com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient.Nutrient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NutrientConverter {

    @TypeConverter
    fun fromNutrientList(value: List<Nutrient>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Nutrient>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toNutrientList(value: String): List<Nutrient> {
        val gson = Gson()
        val type = object : TypeToken<List<Nutrient>>() {}.type
        return gson.fromJson(value, type)
    }
}