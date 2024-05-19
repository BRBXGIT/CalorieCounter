package com.example.caloriecounter.start_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caloriecounter.app.data.repository.AppRepositoryImpl
import com.example.caloriecounter.app.data.userCalorieDb.UserCalorieData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenVM @Inject constructor(
    private val appRepositoryImpl: AppRepositoryImpl
): ViewModel() {

    fun storeCalculatedUserCalorie(
        weight: Int,
        age: Int,
        height: Int,
        activityCoefficient: Double,
        sex: String,
        target: Int
    ) {
        val userWater = 25 * weight
        val userProtein = weight * 2
        val userCarbohydrates = weight * 3

        var userCalorie = if(sex == "Male") {
            (66 + 13.7 * weight + 5 * height - 6.8 * age) * activityCoefficient
        } else {
            (655 + 9.6 * weight + 1.8 * height - 4.7 * age) * activityCoefficient
        }

        if(target == 0) {
            userCalorie *= 0.2
        } else if(target == 1) {
            userCalorie *= 1.5
        }

        viewModelScope.launch(Dispatchers.IO) {
            appRepositoryImpl.upsertUserCalorie(UserCalorieData(
                id = 0,
                requiredCalorieAmount = userCalorie.toInt(),
                requiredWaterAmount = userWater,
                requiredProteinAmount = userProtein,
                requiredFatAmount = weight,
                requiredCarbohydratesAmount = userCarbohydrates,
                weight = weight,
                height = height
            ))
        }
    }
}