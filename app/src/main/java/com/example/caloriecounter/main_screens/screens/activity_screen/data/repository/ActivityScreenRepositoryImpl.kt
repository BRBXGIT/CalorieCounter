package com.example.caloriecounter.main_screens.screens.activity_screen.data.repository

import com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db.Activity
import com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db.ActivityDao
import com.example.caloriecounter.main_screens.screens.activity_screen.domain.ActivityScreenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityScreenRepositoryImpl @Inject constructor(
    private val activityDao: ActivityDao
): ActivityScreenRepository {

    override fun getAllActivities(): Flow<List<Activity>> {
        return activityDao.getAllActivities()
    }

    override suspend fun deleteActivityById(id: Int) {
        activityDao.deleteActivityById(id)
    }

    override suspend fun upsertActivity(activity: Activity) {
        activityDao.upsertActivity(activity)
    }

    override fun updateFeaturedStatusById(isFeature: Boolean, id: Int) {
        activityDao.updateFeaturedStatusById(isFeature, id)
    }
}