package com.example.caloriecounter.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.caloriecounter.auth.google_auth.GoogleAuthUiClient
import com.example.caloriecounter.auth.presentation.AuthScreen
import com.example.caloriecounter.auth.presentation.AuthScreenVM
import com.example.caloriecounter.home_screen.presentation.HomeScreen
import com.example.caloriecounter.start_screen.StartScreen
import com.example.caloriecounter.start_screen.StartScreenVM
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(
    googleAuthUiClient: GoogleAuthUiClient,
    firebaseAuth: FirebaseAuth
) {
    val navController = rememberNavController()

    val authScreenVM = hiltViewModel<AuthScreenVM>()
    val startScreenVM = hiltViewModel<StartScreenVM>()

    val userSignIn = firebaseAuth.currentUser != null
    NavHost(
        navController = navController,
        startDestination = if(userSignIn) {
            HomeScreen
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
                navController = navController
            )
        }

        composable<HomeScreen> {
            HomeScreen()
        }
    }
}