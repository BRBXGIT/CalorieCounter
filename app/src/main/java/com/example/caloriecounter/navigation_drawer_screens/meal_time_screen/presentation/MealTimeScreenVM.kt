package com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_db.MealTime
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.repository.MealTimeScreenRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealTimeScreenVM @Inject constructor(
    private val mealTimeScreenRepositoryImpl: MealTimeScreenRepositoryImpl
): ViewModel() {

    fun getAllMealTime(): Flow<List<MealTime>> {
        return mealTimeScreenRepositoryImpl.getAllMealTime()
    }

    fun insertNewMealTime(mealTime: MealTime) {
        viewModelScope.launch(Dispatchers.IO) {
            mealTimeScreenRepositoryImpl.insertMealTime(mealTime)
        }
    }

    fun updateMealTimeByName(time: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mealTimeScreenRepositoryImpl.updateMealTimeByName(time, name)
        }
    }

    fun updateAlarmTurnOnByName(isOn: Boolean, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mealTimeScreenRepositoryImpl.updateAlarmTurnOnByName(isOn, name)
        }
    }
}