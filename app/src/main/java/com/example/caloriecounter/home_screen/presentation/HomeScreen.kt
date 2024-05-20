package com.example.caloriecounter.home_screen.presentation

import android.text.format.DateUtils
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.caloriecounter.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "Calorie Counter",
                ) },
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_menu),
                            contentDescription = null,
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            var date by rememberSaveable { mutableStateOf(LocalDateTime.now()) }
            val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")

            val selectedDate = date.format(formatter)

            var datePickerOpen by rememberSaveable { mutableStateOf(false) }
            val datePickerState = rememberDatePickerState()
            if(datePickerOpen) {
                DatePickerDialog(
                    onDismissRequest = { datePickerOpen = false },
                    confirmButton = { 
                        TextButton(
                            onClick = {
                                datePickerState.selectedDateMillis?.let {
                                    date = LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
                                }
                                datePickerOpen = false
                            }
                        ) {
                            Text(text = "Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { datePickerOpen = false }
                        ) {
                            Text(text = "Dismiss")
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = true
                    )
                }
            }

            CalendarSection(
                date = selectedDate,
                onPreviousDayClick = { date = date.minusDays(1) },
                onNextDayClick = { date = date.plusDays(1) },
                onCalendarClick = { datePickerOpen = true }
            )
        }
    }
}
