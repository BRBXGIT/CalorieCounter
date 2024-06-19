package com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen.presentation

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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.caloriecounter.R
import com.example.caloriecounter.custom_toasts.ErrorMessage
import com.example.caloriecounter.custom_toasts.SuccessMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealTimeScreen(
    navController: NavHostController,
    context: Context = LocalContext.current
) {
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
                    bottom = innerPadding.calculateBottomPadding()
                ),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(0.dp))

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