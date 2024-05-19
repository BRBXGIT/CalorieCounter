package com.example.caloriecounter.home_screen.di

import android.content.Context
import androidx.room.Room
import com.example.caloriecounter.home_screen.data.day_calorie_db.DayCalorieDao
import com.example.caloriecounter.home_screen.data.day_calorie_db.DayCalorieDb
import com.example.caloriecounter.home_screen.data.repository.HomeScreenRepositoryImpl
import com.example.caloriecounter.home_screen.domain.HomeScreenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeScreenModule {

    @Provides
    @Singleton
    fun provideDayCalorieDao(@ApplicationContext context: Context): DayCalorieDao {
        return Room.databaseBuilder(
            context,
            DayCalorieDb::class.java,
            "Day calorie db"
        ).build().dayCalorieDao()
    }

    @Provides
    @Singleton
    fun provideHomeScreenRepository(dayCalorieDao: DayCalorieDao): HomeScreenRepository {
        return HomeScreenRepositoryImpl(dayCalorieDao)
    }
}