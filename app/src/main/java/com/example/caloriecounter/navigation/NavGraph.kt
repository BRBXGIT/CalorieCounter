package com.example.caloriecounter.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.caloriecounter.auth.google_auth.GoogleAuthUiClient
import com.example.caloriecounter.auth.presentation.AuthScreen
import com.example.caloriecounter.auth.presentation.AuthScreenVM
import com.example.caloriecounter.home_screen.presentation.HomeScreen
import com.example.caloriecounter.home_screen.presentation.HomeScreenVM
import com.example.caloriecounter.start_screen.StartScreen
import com.example.caloriecounter.start_screen.StartScreenVM
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(
    googleAuthUiClient: GoogleAuthUiClient,
    firebaseAuth: FirebaseAuth,
    sharedPreferences: SharedPreferences
) {
    val navController = rememberNavController()

    val authScreenVM = hiltViewModel<AuthScreenVM>()
    val startScreenVM = hiltViewModel<StartScreenVM>()
    val homeScreenVM = hiltViewModel<HomeScreenVM>()

    val userSignIn = firebaseAuth.currentUser != null
    val calorieData = sharedPreferences.getBoolean("calorieDataReceived", false)
    NavHost(
        navController = navController,
        startDestination = if(userSignIn && calorieData) {
            HomeScreen
        } else if((!calorieData) && (userSignIn)) {
            StartScreen
        } else {
            AuthScreen
        }
    ) {
        composable<AuthScreen> {
            AuthScreen(
                authScreenVM = authScreenVM,
                googleAuthUiClient = googleAuthUiClient,
                navController = navController
            )
        }

        composable<StartScreen> {
            StartScreen(
                startScreenVM = startScreenVM,
                navController = navController,
                sharedPreferences = sharedPreferences
            )
        }

        composable<HomeScreen> {
            HomeScreen(
                homeScreenVM = homeScreenVM
            )
        }
    }
}