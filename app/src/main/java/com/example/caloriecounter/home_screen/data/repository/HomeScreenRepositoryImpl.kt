package com.example.caloriecounter.home_screen.data.repository

import com.example.caloriecounter.home_screen.data.day_calorie_db.DayCalorieDao
import com.example.caloriecounter.home_screen.data.day_calorie_db.DayCalorieData
import com.example.caloriecounter.home_screen.domain.HomeScreenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeScreenRepositoryImpl @Inject constructor(
    private val dayCalorieDao: DayCalorieDao
): HomeScreenRepository {

    override suspend fun upsertDayCalorieData(dayCalorieData: DayCalorieData) {
        dayCalorieDao.upsertDayCalories(dayCalorieData)
    }

    override suspend fun updateDayCalorieData(dayCalorieData: DayCalorieData) {
        dayCalorieDao.updateDayCalories(dayCalorieData)
    }

    override fun getDayCalorieByDate(date: String): Flow<DayCalorieData> {
        return dayCalorieDao.getCaloriesByDate(date)
    }
}