package com.example.caloriecounter.navigation

import kotlinx.serialization.Serializable

@Serializable
object AuthScreen

@Serializable
object StartScreen

@Serializable
object RecoverPasswordScreen

@Serializable
object AuthScreensGraph

@Serializable
object HomeScreen

@Serializable
object EatingScreen

@Serializable
object ActivityScreen

@Serializable
object MainScreensGraph

@Serializable
data class AddDishScreen(
    val dishType: String
)

@Serializable
data class DishesScreen(
    val dishType: String
)

@Serializable
object AddActivityScreen

@Serializable
object ProfileScreen

@Serializable
object CalculationsScreen

@Serializable
object MealTimeScreen

@Serializable
object InfoScreen

@Serializable
object SettingsScreen