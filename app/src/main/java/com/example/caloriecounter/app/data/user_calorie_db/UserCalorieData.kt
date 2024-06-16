package com.example.caloriecounter.app.data.user_calorie_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserCalorieData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val requiredCalorieAmount: Int,
    val requiredWaterAmount: Int,
    val weight: Int,
    val height: Int
)
