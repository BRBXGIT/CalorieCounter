package com.example.caloriecounter.main_screens.screens.activity_screen.domain

import com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityScreenRepository {

    suspend fun upsertActivity(activity: Activity)

    suspend fun deleteActivityById(id: Int)

    fun getAllActivities(): Flow<List<Activity>>

    fun updateFeaturedStatusById(isFeature: Boolean, id: Int)
}