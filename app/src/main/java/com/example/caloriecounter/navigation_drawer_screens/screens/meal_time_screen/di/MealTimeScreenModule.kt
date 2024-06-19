package com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen.di

import android.content.Context
import androidx.room.Room
import com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen.data.meal_time_db.MealTimeDao
import com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen.data.meal_time_db.MealTimeDb
import com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen.data.repository.MealTimeScreenRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MealTimeScreenModule {

    @Provides
    @Singleton
    fun provideMealTimeDao(@ApplicationContext context: Context): MealTimeDao {
        return Room.databaseBuilder(
            context,
            MealTimeDb::class.java,
            "Meal time db"
        ).build().mealTimeDao()
    }

    @Provides
    @Singleton
    fun provideMealTimeScreenRepository(mealTimeDao: MealTimeDao): MealTimeScreenRepositoryImpl {
        return MealTimeScreenRepositoryImpl(mealTimeDao)
    }
}