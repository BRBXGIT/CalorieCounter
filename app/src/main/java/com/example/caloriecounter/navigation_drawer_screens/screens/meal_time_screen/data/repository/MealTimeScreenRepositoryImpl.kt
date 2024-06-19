package com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen.data.repository

import com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen.data.meal_time_db.MealTime
import com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen.data.meal_time_db.MealTimeDao
import com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen.domain.MealTimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealTimeScreenRepositoryImpl @Inject constructor(
    private val mealTimeDao: MealTimeDao
): MealTimeRepository {

    override fun getAllMealTime(): Flow<List<MealTime>> {
        return mealTimeDao.getAllMealTime()
    }

    override suspend fun insertMealTime(mealTime: MealTime) {
        mealTimeDao.insertMealTime(mealTime)
    }

    override suspend fun updateMealTimeById(time: Long, id: Int) {
        mealTimeDao.updateMealTimeById(time, id)
    }
}