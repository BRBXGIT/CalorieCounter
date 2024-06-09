package com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db.Meal
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
    suspend fun updateFeaturedStatusById(isFeature: Boolean, id: Int)

    @Query("SELECT * FROM activity WHERE name LIKE '%' || :name || '%'")
    fun getActivitiesByName(name: String): Flow<List<Activity>>
}