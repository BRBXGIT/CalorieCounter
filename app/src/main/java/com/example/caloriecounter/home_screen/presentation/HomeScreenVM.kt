package com.example.caloriecounter.home_screen.presentation

import androidx.lifecycle.ViewModel
import com.example.caloriecounter.app.data.repository.AppRepositoryImpl
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val appRepositoryImpl: AppRepositoryImpl
): ViewModel() {

    fun getUserRequirementCalorieData(): Flow<UserCalorieData> {
        return appRepositoryImpl.getUserCalorieData()
    }
}