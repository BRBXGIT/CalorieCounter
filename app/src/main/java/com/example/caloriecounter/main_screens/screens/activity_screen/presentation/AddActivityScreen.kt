package com.example.caloriecounter.main_screens.screens.activity_screen.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db.Activity
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.duration.DurationDialog
import com.maxkeppeler.sheets.duration.models.DurationConfig
import com.maxkeppeler.sheets.duration.models.DurationFormat
import com.maxkeppeler.sheets.duration.models.DurationSelection
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityScreen(
    activityScreenVM: ActivityScreenVM,
    navController: NavHostController,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var nameError by rememberSaveable { mutableStateOf(false) }

    var calorieAmount by rememberSaveable { mutableStateOf("") }
    calorieAmount = calorieAmount.filter { it.isDigit() }
    var calorieAmountError by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = nameError, key2 = calorieAmountError) {
        delay(3000)
        nameError = false
        calorieAmountError = false
    }

    val durationState = rememberUseCaseState()
    var selectedTimeInSeconds by remember { mutableLongStateOf(240) }

    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val selectedTimeInFormat = LocalTime.ofSecondOfDay(selectedTimeInSeconds).format(formatter)
    DurationDialog(
        state = durationState,
        selection = DurationSelection { newTimeInSeconds ->
            selectedTimeInSeconds = newTimeInSeconds
        },
        config = DurationConfig(
            timeFormat = DurationFormat.HH_MM_SS,
            currentTime = selectedTimeInSeconds,
        )
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Add activity")
                },
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if(name.isBlank()) {
                        nameError = true
                    }
                    if(calorieAmount.isBlank()) {
                        calorieAmountError = true
                    }
                    if(!(calorieAmountError) && (!nameError)) {
                        activityScreenVM.upsertActivity(Activity(
                            name = name,
                            spentCalories = calorieAmount.toInt(),
                            time = selectedTimeInFormat
                        ))
                        navController.navigateUp()
                    }
                },
                modifier = Modifier
                    .height(38.dp)
                    .fillMaxWidth(0.5f),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text(text = "Add activity")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(0.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = nameError,
                maxLines = 1
            )

            OutlinedTextField(
                value = calorieAmount,
                onValueChange = { calorieAmount = it },
                label = { Text(text = "Calories") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = calorieAmountError,
                maxLines = 1
            )
            
            Button(
                onClick = { durationState.show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Select time")
            }

            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}