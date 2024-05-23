package com.example.caloriecounter.main_screens.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriecounter.R
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieData
import com.example.caloriecounter.main_screens.presentation.home_screen.calendar_section.CalendarSection
import com.example.caloriecounter.main_screens.presentation.home_screen.calorie_indicator_section.CalorieIndicatorSection
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeScreenVM: HomeScreenVM
) {
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
                ),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            var date by rememberSaveable { mutableStateOf(LocalDateTime.now()) }
            val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")

            val selectedDate = date.format(formatter)

            val dateDialogState = rememberUseCaseState()
            CalendarDialog(
                state = dateDialogState,
                selection = CalendarSelection.Date { calendarDate ->
                    date = calendarDate.atStartOfDay()
                }
            )

            CalendarSection(
                date = selectedDate,
                onPreviousDayClick = { date = date.minusDays(1) },
                onNextDayClick = { date = date.plusDays(1) },
                onCalendarClick = { dateDialogState.show() }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Indicators calories",
                    modifier = Modifier.padding(start = 14.dp),
                    fontSize = 17.sp
                )

                val userRequirementCalorie = homeScreenVM
                    .getUserRequirementCalorieData()
                    .collectAsState(initial = UserCalorieData(
                        id = 0,
                        requiredCalorieAmount = 0,
                        requiredWaterAmount = 0,
                        weight = 0,
                        height = 0
                    ))
                    .value
                val requiredAmount = userRequirementCalorie.requiredCalorieAmount
                val receivedAmount = 1648
                val spentAmount = 34
                val totalAmount = (receivedAmount - spentAmount)
                CalorieIndicatorSection(
                    receivedAmount = receivedAmount.toString(),
                    requiredAmount = requiredAmount.toString(),
                    spentAmount = spentAmount.toString(),
                    totalAmount = totalAmount.toString()
                )
            }
        }
    }
}
