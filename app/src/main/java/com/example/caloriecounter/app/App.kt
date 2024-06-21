package com.example.caloriecounter.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

//Application class for hilt
@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "cc_notifications",
            "Calorie counter notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}