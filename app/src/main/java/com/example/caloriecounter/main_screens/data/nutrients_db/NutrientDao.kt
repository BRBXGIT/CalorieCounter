package com.example.caloriecounter.main_screens.data.nutrients_db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NutrientDao {

    @Upsert
    suspend fun upsertNutrient(nutrient: Nutrient)

    @Query("DELETE FROM nutrient WHERE id = :id")
    suspend fun deleteNutrientById(id: Int)

    @Query("SELECT * FROM nutrient")
    fun getNutrients(): Flow<List<Nutrient>>

    @Query("UPDATE nutrient SET receivedAmount = :receivedAmount WHERE date = :date")
    suspend fun updateReceivedNutrientAmountByDate(date: String, receivedAmount: Int)

    @Query("SELECT receivedAmount FROM nutrient WHERE date = :date")
    fun getNutrientAmountByDate(date: String): Flow<Int>
}