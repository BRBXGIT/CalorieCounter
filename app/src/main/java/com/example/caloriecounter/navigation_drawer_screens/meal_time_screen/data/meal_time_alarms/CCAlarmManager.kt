package com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_db.MealTimeDao
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
    private val mealTimeDao: MealTimeDao,
    private val context: Context
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun scheduleMealsAlarms() {
        coroutineScope.launch {
            mealTimeDao.getAllMealTime().collect { meals ->
                meals.forEach { meal ->
                    if(meal.alarmTurnOn) {
                        Log.d("XXXX", "start alarm for: ${meal.name}")
                        val intent = Intent(context, CCAlarmReceiver::class.java).apply {
                            putExtra("mealName", meal.name)
                        }
                        val pendingIntent = PendingIntent.getBroadcast(
                            context,
                            meal.id,
                            intent,
                            PendingIntent.FLAG_IMMUTABLE
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

            coroutineScope.cancel()
        }
    }

    fun cancelMealsAlarms(
        cancelAll: Boolean = false,
        mealTimeScreenVM: MealTimeScreenVM
    ) {
        coroutineScope.launch {
            mealTimeDao.getAllMealTime().collect { meals ->
                meals.forEach { meal ->
                    if(cancelAll) {
                        val pendingIntent = PendingIntent.getBroadcast(
                            context,
                            meal.id,
                            Intent(context, CCAlarmReceiver::class.java),
                            PendingIntent.FLAG_IMMUTABLE
                        )
                        alarmManager.cancel(pendingIntent)
                        mealTimeScreenVM.updateAlarmTurnOnByName(false, meal.name)
                    } else {
                        if(meal.alarmTurnOn) {
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

            coroutineScope.cancel()
        }
    }
}