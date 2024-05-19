package com.example.caloriecounter

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.caloriecounter.auth.google_auth.GoogleAuthUiClient
import com.example.caloriecounter.navigation.NavGraph
import com.example.caloriecounter.ui.theme.CalorieCounterTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var googleAuthUiClient: GoogleAuthUiClient

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_CalorieCounter)
        enableEdgeToEdge()

        installSplashScreen()

        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        setContent {
            CalorieCounterTheme {
                NavGraph(
                    googleAuthUiClient = googleAuthUiClient,
                    firebaseAuth = firebaseAuth,
                    sharedPreferences = sharedPreferences
                )
            }
        }
    }
}