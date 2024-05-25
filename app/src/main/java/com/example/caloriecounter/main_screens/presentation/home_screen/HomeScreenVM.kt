package com.example.caloriecounter.main_screens.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caloriecounter.app.data.repository.AppRepositoryImpl
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieData
import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieData
import com.example.caloriecounter.main_screens.data.repository.MainScreensRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val appRepositoryImpl: AppRepositoryImpl,
    private val mainScreensRepositoryImpl: MainScreensRepositoryImpl
): ViewModel() {

    fun getUserRequirementCalorieData(): Flow<UserCalorieData> {
        return appRepositoryImpl.getUserCalorieData()
    }

    fun upsertNewDayCalorieData(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainScreensRepositoryImpl.upsertNewDay(DayCalorieData(
                date = date
            ))
        }
    }

    fun getDayCalorieData(date: String): Flow<DayCalorieData> {
        return mainScreensRepositoryImpl.getCaloriesByDate(date)
    }
}