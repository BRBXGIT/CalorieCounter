package com.example.caloriecounter.main_screens.data.day_nutrient_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "nutrients"
)
data class Nutrient(
    @PrimaryKey(autoGenerate = true)
    val nutrientId: Int,
    val name: String,
    val requiredAmount: Int,
    val color: Long
)
