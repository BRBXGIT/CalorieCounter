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
                            if(timeInMillis <= System.currentTimeMillis()) {
                                add(Calendar.DAY_OF_MONTH, 1)
                            }
                        }

                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            SystemClock.elapsedRealtime() + 5000,
                            pendingIntent
                        )
                    }
                }
            }

            coroutineScope.cancel()
        }
    }

    fun cancelMealsAlarms() {
        coroutineScope.launch {
            mealTimeDao.getAllMealTime().collect { meals ->
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