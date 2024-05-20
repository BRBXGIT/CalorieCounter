package com.example.caloriecounter.home_screen.data.day_calorie_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DayCalorieData(
    @PrimaryKey
    val date: String,
    val receivedCaloriesAmount: Int = 0,
    val spentCaloriesAmount: Int = 0,
    val receivedWaterAmount: Int = 0,
)
