package com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_db.MealTimeDao
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.repository.MealTimeScreenRepositoryImpl
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.presentation.MealTimeScreenVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

//Calorie counter alarm manager)
class CCAlarmManager @Inject constructor(
    private val alarmManager: AlarmManager,
    private val mealTimeScreenRepositoryImpl: MealTimeScreenRepositoryImpl,
    private val context: Context
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun scheduleMealAlarm(name: String) {
        coroutineScope.launch {
            mealTimeScreenRepositoryImpl.getMealTimeByName(name).collect { meal ->
                val intent = Intent(context, CCAlarmReceiver::class.java).apply {
                    putExtra("mealName", meal.name)
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    meal.id,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                val hours = meal.time.split(":")[0].take(2).toInt()
                val minutes = meal.time.split(":")[1].take(2).toInt()
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hours)
                    set(Calendar.MINUTE, minutes)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent,
                )
            }
        }
    }

    fun cancelMealAlarm(name: String) {
        coroutineScope.launch {
            mealTimeScreenRepositoryImpl.getMealTimeByName(name).collect { meal ->
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    meal.id,
                    Intent(context, CCAlarmReceiver::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
                alarmManager.cancel(pendingIntent)
            }
        }
    }
}