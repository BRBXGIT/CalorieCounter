package com.example.caloriecounter.main_screens.screens.eating_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieData
import com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient.Nutrient
import com.example.caloriecounter.main_screens.data.repository.MainScreensRepositoryImpl
import com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db.Meal
import com.example.caloriecounter.main_screens.screens.eating_screen.data.repository.EatingScreenRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EatingScreenVM @Inject constructor(
    private val eatingScreenRepositoryImpl: EatingScreenRepositoryImpl,
    private val mainScreensRepositoryImpl: MainScreensRepositoryImpl
): ViewModel() {

    fun getAllNutrients(): Flow<List<Nutrient>> {
        return mainScreensRepositoryImpl.getAllNutrients()
    }

    fun addDish(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            eatingScreenRepositoryImpl.upsertMeal(meal)
        }
    }

    fun getAllDishes(): Flow<List<Meal>> {
        return eatingScreenRepositoryImpl.getAllMeals()
    }

    fun deleteDishById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            eatingScreenRepositoryImpl.deleteMealById(id)
        }
    }

    fun getTodayCalorieData(date: String): Flow<DayCalorieData> {
        return mainScreensRepositoryImpl.getCaloriesByDate(date)
    }

    fun updateTodayCalorieData(date: String, calorieAmount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mainScreensRepositoryImpl.updateDayCalorieAmountByDate(date, calorieAmount)
        }
    }

    fun getTodayNutrientsData(nutrientId: Int, date: String): Flow<Int> {
        return mainScreensRepositoryImpl.getNutrientAmountByDate(nutrientId, date)
    }

    fun updateTodayNutrientData(nutrientId: Int, amount: Int, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainScreensRepositoryImpl.updateNutrientAmountByDate(nutrientId, date, amount)
        }
    }

    fun getDishByName(name: String, type: String): Flow<List<Meal>> {
        return eatingScreenRepositoryImpl.getDishByName(name, type)
    }

    fun updateFeatureParameter(isFeature: Boolean, id: Int) {
        viewModelScope.launch {
            eatingScreenRepositoryImpl.updateFeatureParameter(isFeature, id)
        }
    }
}