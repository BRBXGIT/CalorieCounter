package com.example.caloriecounter.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.caloriecounter.auth.google_auth.GoogleAuthUiClient
import com.example.caloriecounter.auth.presentation.AuthScreen
import com.example.caloriecounter.auth.presentation.AuthScreenVM
import com.example.caloriecounter.main_screens.screens.MainScreensSharedVM
import com.example.caloriecounter.main_screens.screens.activity_screen.presentation.ActivityScreen
import com.example.caloriecounter.main_screens.screens.activity_screen.presentation.ActivityScreenVM
import com.example.caloriecounter.main_screens.screens.activity_screen.presentation.AddActivityScreen
import com.example.caloriecounter.main_screens.screens.eating_screen.presentation.AddDishScreen
import com.example.caloriecounter.main_screens.screens.eating_screen.presentation.DishesScreen
import com.example.caloriecounter.main_screens.screens.eating_screen.presentation.EatingScreen
import com.example.caloriecounter.main_screens.screens.eating_screen.presentation.EatingScreenVM
import com.example.caloriecounter.main_screens.screens.home_screen.HomeScreen
import com.example.caloriecounter.main_screens.screens.home_screen.HomeScreenVM
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
    val mainScreensSharedVM = viewModel<MainScreensSharedVM>()
    val eatingScreenVM = hiltViewModel<EatingScreenVM>()
    val activityScreenVM = hiltViewModel<ActivityScreenVM>()

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
                homeScreenVM = homeScreenVM,
                mainScreensSharedVM = mainScreensSharedVM,
                navController = navController
            )
        }

        composable<EatingScreen> {
            EatingScreen(
                navController = navController,
                mainScreensSharedVM = mainScreensSharedVM,
            )
        }

        composable<ActivityScreen> {
            ActivityScreen(
                navController = navController,
                mainScreensSharedVM = mainScreensSharedVM,
                activityScreenVM = activityScreenVM
            )
        }

        composable<AddDishScreen> {
            val args = it.toRoute<AddDishScreen>()
            AddDishScreen(
                eatingScreenVM = eatingScreenVM,
                navController = navController,
                typeOfDish = args.dishType
            )
        }

        composable<DishesScreen> {
            val args = it.toRoute<DishesScreen>()
            DishesScreen(
                typeOfDish = args.dishType,
                eatingScreenVM = eatingScreenVM,
                navController = navController,
                mainScreensSharedVM = mainScreensSharedVM
            )
        }

        composable<AddActivityScreen> {
           AddActivityScreen(
               activityScreenVM = activityScreenVM,
               navController = navController
           )
        }
    }
}