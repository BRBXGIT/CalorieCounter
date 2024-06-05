package com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db

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

    @Query("DELETE FROM meal WHERE id = :id")
    suspend fun deleteMealById(id: Int)

    @Query("SELECT * FROM meal WHERE name LIKE '%' || :name || '%' AND type = :type")
    fun getMealsByName(name: String, type: String): Flow<List<Meal>>
}