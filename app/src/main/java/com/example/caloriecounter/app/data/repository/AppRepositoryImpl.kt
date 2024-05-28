package com.example.caloriecounter.app.data.repository

import com.example.caloriecounter.app.domain.AppRepository
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieDao
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//Data which need in all app, such as user's stats(height, weight, calories)
class AppRepositoryImpl @Inject constructor(
    private val userCalorieDao: UserCalorieDao
): AppRepository {

    //Upsert new user to db
    override suspend fun upsertUserCalorie(userCalorieData: UserCalorieData) {
        userCalorieDao.upsertUserCalorie(userCalorieData)
    }

    //Updating user's calories(doesn't used)
    override suspend fun updateUserCalorie(userCalorieData: UserCalorieData) {
        userCalorieDao.updateUserCalorie(userCalorieData)
    }

    //Getting user's data
    override fun getUserCalorieData(): Flow<UserCalorieData> {
        return userCalorieDao.getUserCalorieData()
    }
}