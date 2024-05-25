package com.example.caloriecounter.main_screens.domain

import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieData
import kotlinx.coroutines.flow.Flow

interface MainScreensRepository {

    suspend fun upsertNewDay(dayCalorieData: DayCalorieData)

    suspend fun updateDayCalorieAmountByDate(date: String, amount: Int)

    fun getCaloriesByDate(date: String): Flow<DayCalorieData>
}