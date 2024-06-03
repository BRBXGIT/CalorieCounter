package com.example.caloriecounter.main_screens.screens.eating_screen.di

import android.content.Context
import androidx.room.Room
import com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db.MealDao
import com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db.MealDb
import com.example.caloriecounter.main_screens.screens.eating_screen.data.repository.EatingScreenRepositoryImpl
import com.example.caloriecounter.main_screens.screens.eating_screen.domain.EatingScreenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EatingScreenModule {

    @Provides
    @Singleton
    fun provideMealDao(@ApplicationContext context: Context): MealDao {
        return Room.databaseBuilder(
            context,
            MealDb::class.java,
            "Meal db"
        ).build().mealDao()
    }

    @Provides
    @Singleton
    fun provideEatingScreenRepository(mealDao: MealDao): EatingScreenRepository {
        return EatingScreenRepositoryImpl(mealDao)
    }
}