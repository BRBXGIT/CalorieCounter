package com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MealTimeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMealTime(mealTime: MealTime)

    @Query("UPDATE meal_time SET time = :time WHERE name = :name")
    suspend fun updateMealTimeByName(time: String, name: String)

    @Query("SELECT * FROM meal_time")
    fun getAllMealTime(): Flow<List<MealTime>>

    @Query("UPDATE meal_time SET alarmTurnOn = :isOn WHERE name = :name")
    suspend fun updateAlarmStatusByName(isOn: Boolean, name: String)
}