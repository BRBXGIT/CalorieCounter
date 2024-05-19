package com.example.caloriecounter.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.caloriecounter.auth.google_auth.GoogleAuthUiClient
import com.example.caloriecounter.auth.presentation.AuthScreen
import com.example.caloriecounter.auth.presentation.AuthScreenVM
import com.example.caloriecounter.start_screen.StartScreen

@Composable
fun NavGraph(
    googleAuthUiClient: GoogleAuthUiClient
) {
    val navController = rememberNavController()

    val authScreenVM = hiltViewModel<AuthScreenVM>()
    NavHost(
        navController = navController,
        startDestination = StartScreen
    ) {
        composable<AuthScreen> {
            AuthScreen(
                authScreenVM = authScreenVM,
                googleAuthUiClient = googleAuthUiClient,
                navController = navController
            )
        }

        composable<StartScreen> {
            StartScreen()
        }
    }
}