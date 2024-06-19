package com.example.caloriecounter.app.di

import android.app.AlarmManager
import android.app.Service
import android.content.Context
import androidx.room.Room
import com.example.caloriecounter.app.data.repository.AppRepositoryImpl
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieDao
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieDb
import com.example.caloriecounter.app.domain.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Providing user's dao
    @Provides
    @Singleton
    fun provideUserCalorieDao(@ApplicationContext context: Context): UserCalorieDao {
        return Room.databaseBuilder(
            context,
            UserCalorieDb::class.java,
            "User calorie db"
        ).build().userCalorieDao()
    }

    //Providing app repository
    @Provides
    @Singleton
    fun provideAppRepositoryImpl(userCalorieDao: UserCalorieDao): AppRepository {
        return AppRepositoryImpl(userCalorieDao)
    }

    //Providing alarm manager
    @Provides
    @Singleton
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
        return context.getSystemService(Service.ALARM_SERVICE) as AlarmManager
    }
}