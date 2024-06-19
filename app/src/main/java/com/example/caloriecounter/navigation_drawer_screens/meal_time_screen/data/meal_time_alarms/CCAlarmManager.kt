package com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_db.MealTimeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

//Calorie counter alarm manager)
class CCAlarmManager @Inject constructor(
    private val alarmManager: AlarmManager,
    private val mealTimeDao: MealTimeDao,
    private val context: Context
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun scheduleMealsAlarms() {
        coroutineScope.launch {
            mealTimeDao.getAllMealTime().collect { meals ->
                meals.forEach { meal ->
                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        meal.id,
                        Intent(context, CCAlarmReceiver::class.java),
                        PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        SystemClock.elapsedRealtime() + 5000,
                        pendingIntent
                    )
                }
            }

            coroutineScope.cancel()
        }
    }

    fun cancelMealsAlarms() {
        coroutineScope.launch {
            mealTimeDao.getAllMealTime().collect{ meals ->
                meals.forEach { meal ->
                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        meal.id,
                        Intent(context, CCAlarmReceiver::class.java),
                        PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.cancel(pendingIntent)
                }
            }

            coroutineScope.cancel()
        }
    }
}