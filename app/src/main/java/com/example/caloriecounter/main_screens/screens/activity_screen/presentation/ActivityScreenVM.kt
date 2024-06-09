package com.example.caloriecounter.main_screens.screens.activity_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieData
import com.example.caloriecounter.main_screens.data.repository.MainScreensRepositoryImpl
import com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db.Activity
import com.example.caloriecounter.main_screens.screens.activity_screen.data.repository.ActivityScreenRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityScreenVM @Inject constructor(
    private val activityScreenRepositoryImpl: ActivityScreenRepositoryImpl,
    private val mainScreenRepositoryImpl: MainScreensRepositoryImpl
): ViewModel() {

    fun upsertActivity(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            activityScreenRepositoryImpl.upsertActivity(activity)
        }
    }

    fun deleteActivity(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            activityScreenRepositoryImpl.deleteActivityById(id)
        }
    }

    fun getAllActivities(): Flow<List<Activity>> {
        return activityScreenRepositoryImpl.getAllActivities()
    }

    fun updateActivityFeatureStatus(isFeature: Boolean, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            activityScreenRepositoryImpl.updateFeaturedStatusById(isFeature, id)
        }
    }

    fun getActivitiesByName(name: String): Flow<List<Activity>> {
        return activityScreenRepositoryImpl.findActivitiesByName(name)
    }

    fun updateSpentCaloriesByDate(date: String, calorieAmount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mainScreenRepositoryImpl.updateSpentCaloriesByDate(date, calorieAmount)
        }
    }

    fun getSpentCalorieAmount(date: String): Flow<DayCalorieData> {
        return mainScreenRepositoryImpl.getCaloriesByDate(date)
    }
}