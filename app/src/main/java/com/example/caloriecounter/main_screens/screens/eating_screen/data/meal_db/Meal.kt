package com.example.caloriecounter.main_screens.screens.eating_screen.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient.Nutrient

@Entity(
    tableName = "Meal"
)
data class Meal(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val calories: Int,
    val nutrients: List<Nutrient>,
    val measureInGram: Int,
    val featured: Boolean = false,
    val type: String
)
