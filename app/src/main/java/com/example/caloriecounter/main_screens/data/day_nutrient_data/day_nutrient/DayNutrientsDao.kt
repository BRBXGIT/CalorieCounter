package com.example.caloriecounter.main_screens.data.day_nutrient_data

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
    fun getNutrientsAmountByDate(nutrientId: Int, date: String): Flow<DayNutrientAmount>
}