package com.example.caloriecounter.main_screens.data.nutrients_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Nutrient(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val color: Float,
    val requiredAmount: Int,

    val date: String,
    val receivedAmount: Int
)
