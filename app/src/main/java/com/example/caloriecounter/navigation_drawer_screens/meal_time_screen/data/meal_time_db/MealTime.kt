package com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "meal_time"
)
data class MealTime(
    @PrimaryKey
    val name: String,
    val id: Int,
    val time: String,
    val alarmTurnOn: Boolean = false
)
