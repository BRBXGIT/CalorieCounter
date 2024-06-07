package com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Activity"
)
data class Activity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val spentCalories: Int,
    val time: String,
    val featured: Boolean
)
