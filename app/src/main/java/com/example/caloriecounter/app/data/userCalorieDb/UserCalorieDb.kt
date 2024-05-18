package com.example.caloriecounter.app.data.userCalorieDb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserCalorieData::class],
    version = 1
)
abstract class UserCalorieDb: RoomDatabase() {

    abstract fun userCalorieDao(): UserCalorieDao
}