package com.example.caloriecounter.main_screens.data.day_calorie_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DayCalorieData(
    @PrimaryKey
    val date: String,
    val receivedCaloriesAmount: Int = 0,
    val spentCaloriesAmount: Int = 0,
    val receivedWaterAmount: Int = 0,
    val lastDrinkAt: String = "00:00"
)
