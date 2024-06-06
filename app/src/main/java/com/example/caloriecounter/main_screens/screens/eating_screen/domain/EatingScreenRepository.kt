package com.example.caloriecounter.main_screens.screens.eating_screen.domain

import com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db.Meal
import kotlinx.coroutines.flow.Flow

interface EatingScreenRepository {

    suspend fun upsertMeal(meal: Meal)

    fun getAllMeals(): Flow<List<Meal>>

    suspend fun deleteMealById(id: Int)

    fun getDishByName(name: String, type: String): Flow<List<Meal>>

    suspend fun updateFeatureParameter(isFeature: Boolean, id: Int)
}