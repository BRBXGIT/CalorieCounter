package com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.presentation

import android.Manifest
import android.app.AlarmManager
import android.app.Service
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.caloriecounter.R
import com.example.caloriecounter.custom_toasts.ErrorMessage
import com.example.caloriecounter.custom_toasts.SuccessMessage
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_alarms.CCAlarmManager
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_db.MealTime
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealTimeScreen(
    navController: NavHostController,
    context: Context = LocalContext.current,
    mealTimeScreenVM: MealTimeScreenVM,
    ccAlarmManager: CCAlarmManager
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
                time = 0
            )
        )
    }

    var notificationsGranted by remember { mutableStateOf(false) }
    var notificationsDenied by rememberSaveable { mutableStateOf(false) }

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
            CenterAlignedTopAppBar(
                title = { Text(text = "Meal time") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_navigation_arrow_left),
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(0.dp))

            var enableNotifications by rememberSaveable { mutableStateOf(false) }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Enable notifications")

                Switch(
                    checked = enableNotifications,
                    onCheckedChange = {
                        enableNotifications = it
                        if(enableNotifications) {
                            ccAlarmManager.scheduleMealsAlarms()
                        } else {
                            ccAlarmManager.cancelMealsAlarms()
                        }
                    },
                    thumbContent = {
                        if(enableNotifications) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_check),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                )
            }

            val allMeals = mealTimeScreenVM
                .getAllMealTime()
                .collectAsState(initial = emptyList())
                .value
            AnimatedVisibility(enableNotifications) {
                val formatterFromMillisToDate = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault())
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(allMeals) { meal ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = meal.name,
                                modifier = Modifier.weight(1f),
                                fontSize = 17.sp
                            )

                            Text(
                                text = formatterFromMillisToDate.format(Instant.ofEpochMilli(meal.time)),
                                modifier = Modifier.weight(1f),
                                fontSize = 17.sp
                            )

                            val clockState = rememberUseCaseState()
                            ClockDialog(
                                state = clockState,
                                selection = ClockSelection.HoursMinutes { hours, minutes ->
                                    //This clock dialog has bug with "0", i solved it with this method
                                    val formatter = DateTimeFormatter.ofPattern("HH:mm")
                                    var time = "$hours:$minutes"
                                    if((time.split(":")[0].length < 2) and (time.split(":")[1].length < 2)) {
                                        time = "0$hours:0$minutes"
                                    }
                                    if(time.split(":")[0].length < 2) {
                                        time = "0$hours:$minutes"
                                    }
                                    if(time.split(":")[1].length < 2) {
                                        time = "$hours:0$minutes"
                                    }
                                    val timeInMillis = LocalTime.parse(time, formatter).toSecondOfDay() * 1000L
                                    mealTimeScreenVM.updateMealTimeByName(
                                        time = timeInMillis,
                                        name = meal.name
                                    )
                                },
                                config = ClockConfig(
                                    is24HourFormat = true
                                )
                            )

                            Button(
                                onClick = { clockState.show() },
                                modifier = Modifier
                                    .height(38.dp)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = "Change time",
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
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