package com.example.caloriecounter.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.caloriecounter.auth.google_auth.GoogleAuthUiClient
import com.example.caloriecounter.auth.presentation.AuthScreen
import com.example.caloriecounter.auth.presentation.AuthScreenVM

@Composable
fun NavGraph(
    googleAuthUiClient: GoogleAuthUiClient
) {
    val navController = rememberNavController()

    val authScreenVM = hiltViewModel<AuthScreenVM>()
    NavHost(
        navController = navController,
        startDestination = AuthScreen
    ) {
        composable<AuthScreen> {
            AuthScreen(
                authScreenVM = authScreenVM,
                googleAuthUiClient = googleAuthUiClient
            )
        }
    }
}