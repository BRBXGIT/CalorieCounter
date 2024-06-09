package com.example.caloriecounter.main_screens.screens.activity_screen.domain

import com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityScreenRepository {

    suspend fun upsertActivity(activity: Activity)

    suspend fun deleteActivityById(id: Int)

    fun getAllActivities(): Flow<List<Activity>>

    suspend fun updateFeaturedStatusById(isFeature: Boolean, id: Int)

    fun findActivitiesByName(name: String): Flow<List<Activity>>
}