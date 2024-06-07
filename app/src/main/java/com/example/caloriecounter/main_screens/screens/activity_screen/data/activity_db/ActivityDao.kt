package com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Upsert
    suspend fun upsertActivity(activity: Activity)

    @Query("DELETE FROM activity WHERE id = :id")
    suspend fun deleteActivityById(id: Int)

    @Query("SELECT * FROM activity")
    fun getAllActivities(): Flow<List<Activity>>

    @Query("UPDATE activity SET featured = :isFeature WHERE id = :id")
    fun updateFeaturedStatusById(isFeature: Boolean, id: Int)
}