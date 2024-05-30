package com.example.caloriecounter.main_screens.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caloriecounter.app.data.repository.AppRepositoryImpl
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieData
import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieData
import com.example.caloriecounter.main_screens.data.day_nutrient_data.day_nutrient.DayNutrientAmount
import com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient.Nutrient
import com.example.caloriecounter.main_screens.data.repository.MainScreensRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

    fun updateDayReceivedWaterAmount(date: String, amount: Int, timeOfDrink: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainScreensRepositoryImpl.updateDayWaterAmountByDate(date, amount, timeOfDrink)
        }
    }

    fun getDayCalorieData(date: String): Flow<DayCalorieData> {
        return mainScreensRepositoryImpl.getCaloriesByDate(date)
    }

    fun upsertNutrient(nutrient: Nutrient) {
        viewModelScope.launch(Dispatchers.IO) {
            mainScreensRepositoryImpl.upsertNutrient(nutrient)
        }
    }

    fun getAllNutrients(): Flow<List<Nutrient>> {
        return mainScreensRepositoryImpl.getAllNutrients()
    }

    fun insertNewDayNutrientAmount(nutrientId: Int, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainScreensRepositoryImpl.insertDayNutrientAmount(DayNutrientAmount(
                date, nutrientId
            ))
        }
    }

    fun getNutrientAmountByDate(nutrientId: Int, date: String): Flow<Int> {
        return mainScreensRepositoryImpl.getNutrientAmountByDate(nutrientId, date)
    }
}