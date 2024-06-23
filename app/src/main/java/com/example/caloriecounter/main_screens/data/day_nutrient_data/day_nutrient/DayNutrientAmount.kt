package com.example.caloriecounter.main_screens.data.day_nutrient_data.day_nutrient

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dayNutrientAmount",
    indices = [Index(value = ["date", "nutrientId"], unique = true)]
)
data class DayNutrientAmount(
    val date: String,
    val nutrientId: Int,
    val receivedAmount: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
