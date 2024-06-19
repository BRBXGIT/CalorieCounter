package com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen.domain

import com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen.data.meal_time_db.MealTime
import kotlinx.coroutines.flow.Flow

interface MealTimeRepository {

    suspend fun insertMealTime(mealTime: MealTime)

    suspend fun updateMealTimeById(time: Long, id: Int)

    fun getAllMealTime(): Flow<List<MealTime>>
}