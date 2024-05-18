package com.example.caloriecounter.app.data.userCalorieDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserCalorieData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val requiredCalorieAmount: Int,
    val requiredWaterAmount: Int,
    val requiredProteinAmount: Int,
    val requiredFatAmount: Int,
    val requiredCarbohydratesAmount: Int,
    val weight: Int,
    val height: Int
)
