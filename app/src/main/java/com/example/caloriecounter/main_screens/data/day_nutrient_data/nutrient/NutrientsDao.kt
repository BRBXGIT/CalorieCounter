package com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NutrientsDao {

    @Upsert
    suspend fun upsertNutrient(nutrient: Nutrient)

    @Query("SELECT * FROM nutrients")
    fun getAllNutrients(): Flow<List<Nutrient>>
}