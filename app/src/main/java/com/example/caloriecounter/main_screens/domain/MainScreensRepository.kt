package com.example.caloriecounter.main_screens.domain

import com.example.caloriecounter.main_screens.data.day_calorie_db.DayCalorieData
import com.example.caloriecounter.main_screens.data.nutrients_db.Nutrient
import kotlinx.coroutines.flow.Flow

interface MainScreensRepository {

    suspend fun upsertDayCalorieData(dayCalorieData: DayCalorieData)

    suspend fun updateDayCalorieData(dayCalorieData: DayCalorieData)

    fun getDayCalorieByDate(date: String): Flow<DayCalorieData>

    suspend fun upsertNutrient(nutrient: Nutrient)

    suspend fun deleteNutrientById(id: Int)

    fun getNutrients(): Flow<List<Nutrient>>

    suspend fun updateReceivedNutrientAmountByDate(date: String, receivedAmount: Int)

    fun getNutrientAmountByDate(date: String): Flow<Int>
}