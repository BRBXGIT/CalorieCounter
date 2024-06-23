package com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.presentation

import android.Manifest
import android.app.AlarmManager
import android.app.Service
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.caloriecounter.custom_toasts.ErrorMessage
import com.example.caloriecounter.custom_toasts.SuccessMessage
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_alarms.CCAlarmManager
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_db.MealTime
import com.example.caloriecounter.navigation_drawer_screens.nav_drawer_screens_top_bar.NavigationDrawerScreensTopBar
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealTimeScreen(
    navController: NavHostController,
    context: Context = LocalContext.current,
    mealTimeScreenVM: MealTimeScreenVM,
    ccAlarmManager: CCAlarmManager,
) {
    val mealList = listOf(
        "Breakfast",
        "Lunch",
        "Dinner",
        "Snack"
    )
    mealList.forEachIndexed { index, meal ->
        mealTimeScreenVM.insertNewMealTime(
            MealTime(
                name = meal,
                id = index,
                time = "00:00"
            )
        )
    }

    var notificationsGranted by remember { mutableStateOf(false) }
    var notificationsDenied by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if(isGranted) {
                notificationsGranted = true
            } else {
                notificationsDenied = true
            }
        }
    )

    val notificationPermissionGranted by rememberSaveable {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else {
            mutableStateOf(true)
        }
    }

    LaunchedEffect(Unit) {
        if(!notificationPermissionGranted) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavigationDrawerScreensTopBar(
                navController = navController,
                title = "Meal time"
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                ),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(0.dp))

            val allMeals = mealTimeScreenVM
                .getAllMealTime()
                .collectAsState(initial = emptyList())
                .value
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(allMeals) { meal ->
                    val clockState = rememberUseCaseState()
                    val isOn = meal.alarmTurnOn
                    ClockDialog(
                        state = clockState,
                        selection = ClockSelection.HoursMinutes { hours, minutes ->
                            //This clock dialog has bug with "0", i solved it with this method
                            val formattedHours = if (hours < 10) "0$hours" else "$hours"
                            val formattedMinutes = if (minutes < 10) "0$minutes" else "$minutes"
                            val time = "$formattedHours:$formattedMinutes"

                            mealTimeScreenVM.updateMealTimeByName(
                                time = time,
                                name = meal.name
                            )
                            if(isOn) {
                                ccAlarmManager.scheduleMealAlarm(meal.name)
                            }
                        },
                        config = ClockConfig(
                            is24HourFormat = true
                        )
                    )

                    Surface(
                        onClick = { clockState.show() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(10.dp),
                        shadowElevation = 1.dp
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = meal.name,
                                fontSize = 18.sp,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )

                            Text(
                                text = meal.time,
                                fontSize = 18.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )

                            Switch(
                                checked = isOn,
                                onCheckedChange = { isOn ->
                                    if(isOn) {
                                        mealTimeScreenVM.updateAlarmTurnOnByName(true, meal.name)
                                        ccAlarmManager.scheduleMealAlarm(meal.name)
                                    }
                                    if(!isOn) {
                                        mealTimeScreenVM.updateAlarmTurnOnByName(false, meal.name)
                                        ccAlarmManager.cancelMealAlarm(meal.name)
                                    }
                                },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    }
                }
                
                item { 
                    Spacer(modifier = Modifier.height(1.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            
            AnimatedVisibility(notificationsGranted) {
                SuccessMessage(
                    onTimeEnds = { notificationsGranted = false },
                    text = "Notifications granted"
                )
            }

            AnimatedVisibility(notificationsDenied) {
                ErrorMessage(
                    onTimeEnds = { notificationsDenied = false },
                    text = "You can always enable it in settings)"
                )
            }
            
            val alarmManager = context.getSystemService(Service.ALARM_SERVICE) as AlarmManager
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                AnimatedVisibility(!alarmManager.canScheduleExactAlarms()) {
                    AlarmManagerErrorMessage(context = context)
                }
            }
        }
    }
}