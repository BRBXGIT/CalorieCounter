package com.example.caloriecounter

import android.app.AlarmManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.caloriecounter.app.data.preferences_data_store.PreferencesDataStoreManager
import com.example.caloriecounter.auth.google_auth.GoogleAuthUiClient
import com.example.caloriecounter.navigation.NavGraph
import com.example.caloriecounter.navigation_drawer_screens.meal_time_screen.data.meal_time_alarms.CCAlarmManager
import com.example.caloriecounter.ui.theme.CalorieCounterTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var googleAuthUiClient: GoogleAuthUiClient

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var ccAlarmManager: CCAlarmManager

    @Inject
    lateinit var preferencesDataStoreManager: PreferencesDataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_CalorieCounter)

        val locale = Locale("en")
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        enableEdgeToEdge()
        installSplashScreen()

        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        setContent {
            CalorieCounterTheme(
                preferencesDataStoreManager = preferencesDataStoreManager
            ) {
                NavGraph(
                    googleAuthUiClient = googleAuthUiClient,
                    firebaseAuth = firebaseAuth,
                    sharedPreferences = sharedPreferences,
                    ccAlarmManager = ccAlarmManager,
                    preferencesDataStoreManager = preferencesDataStoreManager
                )
            }
        }
    }
}