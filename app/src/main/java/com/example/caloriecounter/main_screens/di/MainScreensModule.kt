package com.example.caloriecounter.main_screens.di

import android.content.Context
import androidx.room.Room
import com.example.caloriecounter.main_screens.data.day_calorie_db.DayCalorieDao
import com.example.caloriecounter.main_screens.data.day_calorie_db.DayCalorieDb
import com.example.caloriecounter.main_screens.data.nutrients_db.NutrientDao
import com.example.caloriecounter.main_screens.data.nutrients_db.NutrientDb
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
object MainScreensModule {

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
    fun provideNutrientDao(@ApplicationContext context: Context): NutrientDao {
        return Room.databaseBuilder(
            context,
            NutrientDb::class.java,
            "nutrient db"
        ).build().nutrientDao()
    }

    @Provides
    @Singleton
    fun provideMainScreensRepository(dayCalorieDao: DayCalorieDao, nutrientDao: NutrientDao): MainScreensRepository {
        return MainScreensRepositoryImpl(dayCalorieDao, nutrientDao)
    }
}