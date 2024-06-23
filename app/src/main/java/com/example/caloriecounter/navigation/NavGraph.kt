package com.example.caloriecounter.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.caloriecounter.app.data.preferences_data_store.PreferencesDataStoreManager
import com.example.caloriecounter.auth.google_auth.GoogleAuthUiClient
import com.example.caloriecounter.auth.presentation.AuthScreen
import com.example.caloriecounter.auth.presentation.AuthVM
import com.example.caloriecounter.auth.presentation.RecoverPasswordScreen
import com.example.caloriecounter.main_screens.screens.MainScreensSharedVM
import com.example.caloriecounter.main_screens.screens.activity_screen.presentation.ActivityScreen
import com.example.caloriecounter.main_screens.screens.activity_screen.presentation.ActivityScreenVM
import com.example.caloriecounter.main_screens.screens.activity_screen.presentation.AddActivityScreen
import com.example.caloriecounter.main_screens.screens.eating_screen.presentation.AddDishScreen
import com.example.caloriecounter.main_screens.screens.eating_screen.presentation.EatingScreen
import com.example.caloriecounter.main_screens.screens.eating_screen.presentation.EatingScreenVM
import com.example.caloriecounter.main_screens.screens.eating_screen.presentation.dishes_screen.DishesScreen
import com.example.caloriecounter.main_screens.screens.home_screen.HomeScreen
import com.example.caloriecounter.main_screens.screens.home_screen.HomeScreenVM
import com.example.caloriecounter.navigation_drawer_screens.calculations_screen.CalculationsScreen
import com.example.caloriecounter.navigation_drawer_screens.calculations_screen.CalculationsScreenVM
import com.example.caloriecounter.navigation_drawer_screens.info_screen.InfoScreen
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_alarms.CCAlarmManager
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.presentation.MealTimeScreen
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.presentation.MealTimeScreenVM
import com.example.caloriecounter.navigation_drawer_screens.profile_screen.presentation.ProfileScreen
import com.example.caloriecounter.navigation_drawer_screens.profile_screen.presentation.ProfileScreenVM
import com.example.caloriecounter.navigation_drawer_screens.settings_screen.SettingsScreen
import com.example.caloriecounter.auth.start_screen.StartScreen
import com.example.caloriecounter.auth.start_screen.StartScreenVM
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(
    googleAuthUiClient: GoogleAuthUiClient,
    firebaseAuth: FirebaseAuth,
    sharedPreferences: SharedPreferences,
    ccAlarmManager: CCAlarmManager,
    preferencesDataStoreManager: PreferencesDataStoreManager,
    navController: NavHostController
) {
    val mainScreensSharedVM = viewModel<MainScreensSharedVM>()

    val userSignIn = firebaseAuth.currentUser != null
    val calorieData = sharedPreferences.getBoolean("calorieDataReceived", false)
    NavHost(
        navController = navController,
        startDestination = if(userSignIn && calorieData) MainScreensGraph else AuthScreensGraph
    ) {
        navigation<AuthScreensGraph>(
            startDestination = if((!calorieData) && (userSignIn)) {
                StartScreen
            } else {
                AuthScreen
            }
        ) {
            composable<AuthScreen> {
                val authVM = hiltViewModel<AuthVM>()
                AuthScreen(
                    authVM = authVM,
                    googleAuthUiClient = googleAuthUiClient,
                    navController = navController
                )
            }

            composable<RecoverPasswordScreen> {
                val authVM = hiltViewModel<AuthVM>()
                RecoverPasswordScreen(
                    navController = navController,
                    authVM = authVM
                )
            }

            composable<StartScreen> {
                val startScreenVM = hiltViewModel<StartScreenVM>()
                StartScreen(
                    startScreenVM = startScreenVM,
                    navController = navController,
                    sharedPreferences = sharedPreferences
                )
            }
        }

        navigation<MainScreensGraph>(
            startDestination = HomeScreen
        ) {
            composable<HomeScreen> {
                val homeScreenVM = hiltViewModel<HomeScreenVM>()
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
                val activityScreenVM = hiltViewModel<ActivityScreenVM>()
                ActivityScreen(
                    navController = navController,
                    mainScreensSharedVM = mainScreensSharedVM,
                    activityScreenVM = activityScreenVM
                )
            }

            composable<AddDishScreen> {
                val eatingScreenVM = hiltViewModel<EatingScreenVM>()
                val args = it.toRoute<AddDishScreen>()
                AddDishScreen(
                    eatingScreenVM = eatingScreenVM,
                    navController = navController,
                    typeOfDish = args.dishType
                )
            }

            composable<DishesScreen> {
                val eatingScreenVM = hiltViewModel<EatingScreenVM>()
                val args = it.toRoute<DishesScreen>()
                DishesScreen(
                    typeOfDish = args.dishType,
                    eatingScreenVM = eatingScreenVM,
                    navController = navController,
                    mainScreensSharedVM = mainScreensSharedVM
                )
            }

            composable<AddActivityScreen> {
                val activityScreenVM = hiltViewModel<ActivityScreenVM>()
                AddActivityScreen(
                    activityScreenVM = activityScreenVM,
                    navController = navController
                )
            }
        }

        composable<ProfileScreen> {
            val profileScreenVM = hiltViewModel<ProfileScreenVM>()
            ProfileScreen(
                navController = navController,
                firebaseAuth = firebaseAuth,
                profileScreenVM = profileScreenVM
            )
        }

        composable<CalculationsScreen> {
            val calculationsScreenVM = hiltViewModel<CalculationsScreenVM>()
            CalculationsScreen(
                navController = navController,
                calculationsScreenVM = calculationsScreenVM
            )
        }

        composable<MealTimeScreen> {
            val mealTimeScreenVM = hiltViewModel<MealTimeScreenVM>()
            MealTimeScreen(
                navController = navController,
                mealTimeScreenVM = mealTimeScreenVM,
                ccAlarmManager = ccAlarmManager,
            )
        }

        composable<InfoScreen> {
            InfoScreen(navController = navController)
        }

        composable<SettingsScreen> {
            SettingsScreen(
                preferencesDataStoreManager = preferencesDataStoreManager,
                navController = navController
            )
        }
    }
}