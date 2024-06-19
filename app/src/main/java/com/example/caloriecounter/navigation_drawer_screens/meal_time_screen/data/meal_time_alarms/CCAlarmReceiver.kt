package com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class CCAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("receiver", "received")
    }
}