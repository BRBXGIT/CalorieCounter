package com.example.caloriecounter.main_screens.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caloriecounter.app.data.repository.AppRepositoryImpl
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieData
import com.example.caloriecounter.main_screens.data.nutrients_db.Nutrient
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

    fun getNutrients(): Flow<List<Nutrient>> {
        return mainScreensRepositoryImpl.getNutrients()
    }

    fun upsertNewNutrient(nutrient: Nutrient) {
        viewModelScope.launch(Dispatchers.IO) {
            mainScreensRepositoryImpl.upsertNutrient(nutrient)
        }
    }
}