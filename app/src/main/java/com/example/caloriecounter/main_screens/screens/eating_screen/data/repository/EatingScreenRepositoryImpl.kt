package com.example.caloriecounter.main_screens.screens.eating_screen.data.repository

import com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db.Meal
import com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db.MealDao
import com.example.caloriecounter.main_screens.screens.eating_screen.domain.EatingScreenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EatingScreenRepositoryImpl @Inject constructor(
    private val mealDao: MealDao
): EatingScreenRepository {

    override fun getAllMeals(): Flow<List<Meal>> {
        return mealDao.getAllMeals()
    }

    override fun getBreakfastMeal(): Flow<List<Meal>> {
        return mealDao.getBreakfastMeal()
    }

    override suspend fun upsertMeal(meal: Meal) {
        mealDao.upsertMeal(meal)
    }

    override fun getDinnerMeal(): Flow<List<Meal>> {
        return mealDao.getDinnerMeal()
    }

    override suspend fun deleteMealById(id: Int) {
        mealDao.deleteMealById(id)
    }

    override fun getFeaturedMeals(): Flow<List<Meal>> {
        return mealDao.getFeaturedMeals()
    }

    override fun getLunchMeal(): Flow<List<Meal>> {
        return mealDao.getLunchMeal()
    }

    override fun getSnackMeal(): Flow<List<Meal>> {
        return mealDao.getSnackMeal()
    }
}