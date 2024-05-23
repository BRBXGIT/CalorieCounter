package com.example.caloriecounter.main_screens.data.day_calorie_db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DayCalorieDao {

    @Upsert
    suspend fun upsertDayCalories(dayCalorieData: DayCalorieData)

    @Update
    suspend fun updateDayCalories(dayCalorieData: DayCalorieData)

    @Query("SELECT * FROM daycaloriedata WHERE date = :date")
    fun getCaloriesByDate(date: String): Flow<DayCalorieData>
}