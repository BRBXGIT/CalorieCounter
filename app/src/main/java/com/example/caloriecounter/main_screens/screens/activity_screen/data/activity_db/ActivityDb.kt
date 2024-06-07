package com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Activity::class],
    version = 1
)
abstract class ActivityDb: RoomDatabase() {

    abstract fun activityDao(): ActivityDao
}