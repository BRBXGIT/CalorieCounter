package com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen.data.meal_time_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "meal_time"
)
data class MealTime(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val time: Long
)
