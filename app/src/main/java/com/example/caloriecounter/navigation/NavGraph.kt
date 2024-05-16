package com.example.caloriecounter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.caloriecounter.auth.presentation.AuthScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AuthScreen
    ) {
        composable<AuthScreen> {
            AuthScreen()
        }
    }
}