package com.example.caloriecounter.main_screens.data.repository

import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieDao
import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieData
import com.example.caloriecounter.main_screens.data.day_nutrient_data.NutrientDb
import com.example.caloriecounter.main_screens.data.day_nutrient_data.day_nutrient.DayNutrientAmount
import com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient.Nutrient
import com.example.caloriecounter.main_screens.domain.MainScreensRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainScreensRepositoryImpl @Inject constructor(
    private val dayCalorieDao: DayCalorieDao,
    private val nutrientDb: NutrientDb
): MainScreensRepository {

    override suspend fun upsertNewDay(dayCalorieData: DayCalorieData) {
        dayCalorieDao.upsertDayCalories(dayCalorieData)
    }

    override suspend fun updateDayCalorieAmountByDate(date: String, amount: Int) {
        dayCalorieDao.updateDayCalorieAmountByDate(date, amount)
    }

    override suspend fun updateDayWaterAmountByDate(date: String, amount: Int, timeOfDrink: String) {
        dayCalorieDao.updateDayWaterAmountByDate(date, amount, timeOfDrink)
    }

    override fun getCaloriesByDate(date: String): Flow<DayCalorieData> {
        return dayCalorieDao.getCaloriesByDate(date)
    }

    private val nutrientDao = nutrientDb.nutrientDao()
    override suspend fun upsertNutrient(nutrient: Nutrient) {
        nutrientDao.upsertNutrient(nutrient)
    }

    override fun getAllNutrients(): Flow<List<Nutrient>> {
        return nutrientDao.getAllNutrients()
    }

    private val dayNutrientDao = nutrientDb.dayNutrientDao()
    override suspend fun insertDayNutrientAmount(dayNutrientAmount: DayNutrientAmount) {
        dayNutrientDao.insertNewDayNutrient(dayNutrientAmount)
    }

    override fun getNutrientAmountByDate(nutrientId: Int, date: String): Flow<Int> {
        return dayNutrientDao.getNutrientAmountByDate(nutrientId, date)
    }

    override suspend fun updateNutrientAmountByDate(nutrientId: Int, date: String, amount: Int) {
        dayNutrientDao.updateNutrientAmountByDate(nutrientId, date, amount)
    }
}