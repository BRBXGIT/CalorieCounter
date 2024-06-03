package com.example.caloriecounter.main_screens.screens.eating_screen.domain

import com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db.Meal
import kotlinx.coroutines.flow.Flow

interface EatingScreenRepository {

    suspend fun upsertMeal(meal: Meal)

    fun getAllMeals(): Flow<List<Meal>>

    fun getFeaturedMeals(): Flow<List<Meal>>

    fun getBreakfastMeal(): Flow<List<Meal>>

    fun getLunchMeal(): Flow<List<Meal>>

    fun getDinnerMeal(): Flow<List<Meal>>

    fun getSnackMeal(): Flow<List<Meal>>

    suspend fun deleteMealById(id: Int)
}