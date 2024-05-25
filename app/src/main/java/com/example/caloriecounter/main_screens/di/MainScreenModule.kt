package com.example.caloriecounter.main_screens.di

import android.content.Context
import androidx.room.Room
import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieDao
import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieDb
import com.example.caloriecounter.main_screens.data.repository.MainScreensRepositoryImpl
import com.example.caloriecounter.main_screens.domain.MainScreensRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainScreenModule {

    @Provides
    @Singleton
    fun provideDayCalorieDao(@ApplicationContext context: Context): DayCalorieDao {
        return Room.databaseBuilder(
            context,
            DayCalorieDb::class.java,
            "day calorie db"
        ).build().dayCalorieDao()
    }

    @Provides
    @Singleton
    fun provideMainScreensRepository(dayCalorieDao: DayCalorieDao): MainScreensRepository {
        return MainScreensRepositoryImpl(dayCalorieDao)
    }
}