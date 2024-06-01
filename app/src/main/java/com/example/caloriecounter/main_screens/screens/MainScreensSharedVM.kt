package com.example.caloriecounter.main_screens.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainScreensSharedVM: ViewModel() {

    private var date = LocalDateTime.now()
    private val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")

    var selectedDate = mutableStateOf(date.format(formatter))
        private set

    fun dateMinusDay() {
        date = date.minusDays(1)
        selectedDate.value = date.format(formatter)
    }

    fun datePlusDay() {
        date = date.plusDays(1)
        selectedDate.value = date.format(formatter)
    }

    fun setDate(newDate: LocalDateTime) {
        date = newDate
        selectedDate.value = date.format(formatter)
    }
}