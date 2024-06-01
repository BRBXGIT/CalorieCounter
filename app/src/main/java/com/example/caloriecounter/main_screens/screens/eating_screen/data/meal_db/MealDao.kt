package com.example.caloriecounter.main_screens.screens.eating_screen.data

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Upsert
    suspend fun upsertMeal(meal: Meal)

    @Query("SELECT * FROM meal")
    fun getAllMeals(): Flow<List<Meal>>

    @Query("SELECT * FROM Meal WHERE featured = 1")
    fun getFeaturedMeals(): Flow<List<Meal>>

    @Query("SELECT * FROM meal WHERE type = 'Breakfast'")
    fun getBreakfastMeal(): Flow<List<Meal>>

    @Query("SELECT * FROM meal WHERE type = 'Lunch'")
    fun getLunchMeal(): Flow<List<Meal>>

    @Query("SELECT * FROM meal WHERE type = 'Dinner'")
    fun getDinnerMeal(): Flow<List<Meal>>

    @Query("SELECT * FROM meal WHERE type = 'Snack'")
    fun getSnackMeal(): Flow<List<Meal>>

    @Query("DELETE FROM meal WHERE id = :id")
    suspend fun deleteMealById(id: Int)
}