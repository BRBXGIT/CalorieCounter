package com.example.caloriecounter.app.domain

import com.example.caloriecounter.app.data.userCalorieDb.UserCalorieData
import kotlinx.coroutines.flow.Flow


interface AppRepository {

    suspend fun upsertUserCalorie(userCalorieData: UserCalorieData)

    suspend fun updateUserCalorie(userCalorieData: UserCalorieData)

    fun getUserCalorieData(): Flow<UserCalorieData>
}