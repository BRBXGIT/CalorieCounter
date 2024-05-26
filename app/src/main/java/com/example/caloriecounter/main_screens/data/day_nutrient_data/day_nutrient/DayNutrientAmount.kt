package com.example.caloriecounter.main_screens.data.day_nutrient_data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "dayNutrientAmount",
    foreignKeys = [ForeignKey(
        entity = Nutrient::class,
        parentColumns = arrayOf("nutrientId"),
        childColumns = arrayOf("nutrientId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class DayNutrientAmount(
    @PrimaryKey
    val date: String,
    val nutrientId: Int,
    val receivedAmount: Int = 0
)
