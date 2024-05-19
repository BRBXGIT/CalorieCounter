package com.example.caloriecounter.app.data.user_calorie_db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserCalorieDao {

    @Upsert
    suspend fun upsertUserCalorie(userCalorieData: UserCalorieData)

    @Update
    suspend fun updateUserCalorie(userCalorieData: UserCalorieData)

    @Query("SELECT * FROM usercaloriedata")
    fun getUserCalorieData(): Flow<UserCalorieData>
}