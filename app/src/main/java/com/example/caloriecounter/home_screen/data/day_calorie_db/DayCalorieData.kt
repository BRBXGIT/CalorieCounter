package com.example.caloriecounter.home_screen.data.day_calorie_db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieData

@Entity
data class DayCalorieData(
    @PrimaryKey
    val date: String,
    val receivedCaloriesAmount: Int,
    val spentCaloriesAmount: Int,
    val receivedWaterAmount: Int,
)
