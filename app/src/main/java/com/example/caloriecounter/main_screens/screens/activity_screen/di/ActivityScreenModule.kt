package com.example.caloriecounter.main_screens.screens.activity_screen.di

import android.content.Context
import androidx.room.Room
import com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db.ActivityDao
import com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db.ActivityDb
import com.example.caloriecounter.main_screens.screens.activity_screen.data.repository.ActivityScreenRepositoryImpl
import com.example.caloriecounter.main_screens.screens.activity_screen.domain.ActivityScreenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ActivityScreenModule {

    @Provides
    @Singleton
    fun provideActivityDao(@ApplicationContext context: Context): ActivityDao {
        return Room.databaseBuilder(
            context,
            ActivityDb::class.java,
            "Activity db"
        ).build().activityDao()
    }

    @Provides
    @Singleton
    fun provideActivityScreenRepository(activityDao: ActivityDao): ActivityScreenRepository {
        return ActivityScreenRepositoryImpl(activityDao)
    }
}