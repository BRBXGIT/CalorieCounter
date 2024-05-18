package com.example.caloriecounter

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import com.example.caloriecounter.auth.google_auth.GoogleAuthUiClient
import com.example.caloriecounter.navigation.NavGraph
import com.example.caloriecounter.ui.theme.CalorieCounterTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var googleAuthUiClient: GoogleAuthUiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_CalorieCounter)
        enableEdgeToEdge()

        installSplashScreen()

        setContent {
            CalorieCounterTheme {
                NavGraph(
                    googleAuthUiClient = googleAuthUiClient,
                )
            }
        }
    }
}