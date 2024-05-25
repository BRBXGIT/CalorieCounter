package com.example.caloriecounter.main_screens.data.day_calorie_data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DayCalorieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsertDayCalories(dayCalorieData: DayCalorieData)

    @Query("UPDATE daycaloriedata SET receivedCaloriesAmount = :amount WHERE date = :date")
    suspend fun updateDayCalorieAmountByDate(date: String, amount: Int)

    @Query("SELECT * FROM daycaloriedata WHERE date = :date")
    fun getCaloriesByDate(date: String): Flow<DayCalorieData>
}