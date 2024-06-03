package com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "nutrients"
)
data class Nutrient(
    @PrimaryKey(autoGenerate = true)
    val nutrientId: Int = 0,
    val name: String,
    val requiredAmount: Int,
    val willReceiveAmount: Int = 0,
    val color: Long
)
