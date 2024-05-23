package com.example.caloriecounter.main_screens.data.repository

import com.example.caloriecounter.main_screens.data.day_calorie_db.DayCalorieDao
import com.example.caloriecounter.main_screens.data.day_calorie_db.DayCalorieData
import com.example.caloriecounter.main_screens.data.nutrients_db.Nutrient
import com.example.caloriecounter.main_screens.data.nutrients_db.NutrientDao
import com.example.caloriecounter.main_screens.domain.MainScreensRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainScreensRepositoryImpl @Inject constructor(
    private val dayCalorieDao: DayCalorieDao,
    private val nutrientDao: NutrientDao
): MainScreensRepository {

    override suspend fun upsertDayCalorieData(dayCalorieData: DayCalorieData) {
        dayCalorieDao.upsertDayCalories(dayCalorieData)
    }

    override suspend fun updateDayCalorieData(dayCalorieData: DayCalorieData) {
        dayCalorieDao.updateDayCalories(dayCalorieData)
    }

    override fun getDayCalorieByDate(date: String): Flow<DayCalorieData> {
        return dayCalorieDao.getCaloriesByDate(date)
    }

    override fun getNutrients(): Flow<List<Nutrient>> {
        return nutrientDao.getNutrients()
    }

    override suspend fun deleteNutrientById(id: Int) {
        nutrientDao.deleteNutrientById(id)
    }

    override fun getNutrientAmountByDate(date: String): Flow<Int> {
        return nutrientDao.getNutrientAmountByDate(date)
    }

    override suspend fun upsertNutrient(nutrient: Nutrient) {
        nutrientDao.upsertNutrient(nutrient)
    }

    override suspend fun updateReceivedNutrientAmountByDate(date: String, receivedAmount: Int) {
        nutrientDao.updateReceivedNutrientAmountByDate(date, receivedAmount)
    }
}