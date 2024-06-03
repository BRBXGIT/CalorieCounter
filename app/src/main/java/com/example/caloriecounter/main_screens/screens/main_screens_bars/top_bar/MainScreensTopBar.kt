package com.example.caloriecounter.main_screens.presentation.main_screens_bars.top_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.presentation.MainScreensSharedVM
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreensTopBar(
    mainScreensSharedVM: MainScreensSharedVM
) {
    CenterAlignedTopAppBar(
        title = {
            val dateDialogState = rememberUseCaseState()
            CalendarDialog(
                state = dateDialogState,
                selection = CalendarSelection.Date { calendarDate ->
                    mainScreensSharedVM.setDate(calendarDate.atStartOfDay())
                }
            )

            CalendarSection(
                date = mainScreensSharedVM.selectedDate.value,
                onPreviousDayClick = { mainScreensSharedVM.dateMinusDay() },
                onNextDayClick = { mainScreensSharedVM.datePlusDay() },
                onCalendarClick = { dateDialogState.show() },
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = null,
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            scrolledContainerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    )
}