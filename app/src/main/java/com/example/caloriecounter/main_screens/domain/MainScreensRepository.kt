package com.example.caloriecounter.main_screens.domain

import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieData
import com.example.caloriecounter.main_screens.data.day_nutrient_data.day_nutrient.DayNutrientAmount
import com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient.Nutrient
import kotlinx.coroutines.flow.Flow

interface MainScreensRepository {

    suspend fun upsertNewDay(dayCalorieData: DayCalorieData)

    suspend fun updateDayCalorieAmountByDate(date: String, amount: Int)

    fun getCaloriesByDate(date: String): Flow<DayCalorieData>

    suspend fun upsertNutrient(nutrient: Nutrient)

    fun getAllNutrients(): Flow<List<Nutrient>>

    suspend fun insertDayNutrientAmount(dayNutrientAmount: DayNutrientAmount)

    fun getNutrientAmountByDate(nutrientId: Int, date: String): Flow<Int>
}