package com.example.caloriecounter.app.di

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.room.Room
import com.example.caloriecounter.R
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

    @Provides
    @Singleton
    fun provideBasicNotification(@ApplicationContext context: Context): Notification {
        return NotificationCompat.Builder(context, "0")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Time to eat)")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }
}