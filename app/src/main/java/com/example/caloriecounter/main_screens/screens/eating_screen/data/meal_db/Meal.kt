package com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient.Nutrient

//Named "meal" due to my error, further will be named "dish"
@Entity(
    tableName = "Meal"
)
data class Meal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val calories: Int,
    val nutrients: List<Nutrient>,
    val measureInGram: Int,
    val featured: Boolean = false,
    val type: String
)
