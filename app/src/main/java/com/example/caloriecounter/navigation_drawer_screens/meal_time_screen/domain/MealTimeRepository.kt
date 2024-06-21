package com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.domain

import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_db.MealTime
import kotlinx.coroutines.flow.Flow

interface MealTimeRepository {

    suspend fun insertMealTime(mealTime: MealTime)

    suspend fun updateMealTimeByName(time: String, name: String)

    fun getAllMealTime(): Flow<List<MealTime>>

    suspend fun updateAlarmTurnOnByName(isOn: Boolean, name: String)
}