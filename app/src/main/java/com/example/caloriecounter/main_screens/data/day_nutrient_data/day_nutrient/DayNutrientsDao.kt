package com.example.caloriecounter.main_screens.data.day_nutrient_data.day_nutrient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DayNutrientsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewDayNutrient(dayNutrientAmount: DayNutrientAmount)

    @Query("SELECT receivedAmount FROM dayNutrientAmount WHERE nutrientId = :nutrientId AND date = :date")
    fun getNutrientAmountByDate(nutrientId: Int, date: String): Flow<Int>

    @Query("UPDATE dayNutrientAmount SET receivedAmount = :amount WHERE date = :date AND nutrientId = :nutrientId")
    suspend fun updateNutrientAmountByDate(nutrientId: Int, date: String, amount: Int)
}