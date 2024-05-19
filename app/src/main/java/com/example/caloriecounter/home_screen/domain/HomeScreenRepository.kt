package com.example.caloriecounter.home_screen.domain

import com.example.caloriecounter.home_screen.data.day_calorie_db.DayCalorieData
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepository {

    suspend fun upsertDayCalorieData(dayCalorieData: DayCalorieData)

    suspend fun updateDayCalorieData(dayCalorieData: DayCalorieData)

    fun getDayCalorieByDate(date: String): Flow<DayCalorieData>
}