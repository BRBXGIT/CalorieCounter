package com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_alarms

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.caloriecounter.R

class CCAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val mealName = intent?.getStringExtra("mealName")

        val notification = NotificationCompat.Builder(context, "cc_notifications")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Eating time")
            .setContentText("Time to eat $mealName")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(mealName.hashCode(), notification)
    }
}