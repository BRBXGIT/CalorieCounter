package com.example.caloriecounter.app.di

import android.content.Context
import androidx.room.Room
import com.example.caloriecounter.app.domain.AppRepository
import com.example.caloriecounter.app.data.repository.AppRepositoryImpl
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieDao
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserCalorieDao(@ApplicationContext context: Context): UserCalorieDao {
        return Room.databaseBuilder(
            context,
            UserCalorieDb::class.java,
            "User calorie db"
        ).build().userCalorieDao()
    }

    @Provides
    @Singleton
    fun provideAppRepositoryImpl(userCalorieDao: UserCalorieDao): AppRepository {
        return AppRepositoryImpl(userCalorieDao)
    }
}