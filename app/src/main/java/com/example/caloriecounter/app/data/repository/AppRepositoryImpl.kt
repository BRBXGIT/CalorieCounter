package com.example.caloriecounter.app.data.repository

import com.example.caloriecounter.app.domain.AppRepository
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieDao
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val userCalorieDao: UserCalorieDao
): AppRepository {

    override suspend fun upsertUserCalorie(userCalorieData: UserCalorieData) {
        userCalorieDao.upsertUserCalorie(userCalorieData)
    }

    override suspend fun updateUserCalorie(userCalorieData: UserCalorieData) {
        userCalorieDao.updateUserCalorie(userCalorieData)
    }

    override fun getUserCalorieData(): Flow<UserCalorieData> {
        return userCalorieDao.getUserCalorieData()
    }
}