package com.example.caloriecounter.main_screens.screens.eating_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}