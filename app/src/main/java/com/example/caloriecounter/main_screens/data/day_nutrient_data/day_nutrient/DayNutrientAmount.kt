package com.example.caloriecounter.main_screens.data.day_nutrient_data.day_nutrient

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient.Nutrient

@Entity(
    tableName = "dayNutrientAmount",
)
data class DayNutrientAmount(
    val date: String,
    val nutrientId: Int,
    val receivedAmount: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
