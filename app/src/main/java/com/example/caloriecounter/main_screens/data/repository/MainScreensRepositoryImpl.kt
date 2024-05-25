package com.example.caloriecounter.main_screens.data.repository

import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieDao
import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieData
import com.example.caloriecounter.main_screens.domain.MainScreensRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainScreensRepositoryImpl @Inject constructor(
    private val dayCalorieDao: DayCalorieDao
): MainScreensRepository {

    override suspend fun upsertNewDay(dayCalorieData: DayCalorieData) {
        dayCalorieDao.upsertDayCalories(dayCalorieData)
    }

    override suspend fun updateDayCalorieAmountByDate(date: String, amount: Int) {
        dayCalorieDao.updateDayCalorieAmountByDate(date, amount)
    }

    override fun getCaloriesByDate(date: String): Flow<DayCalorieData> {
        return dayCalorieDao.getCaloriesByDate(date)
    }
}