package com.example.caloriecounter.main_screens.screens.activity_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.caloriecounter.main_screens.screens.main_screens_bars.bottom_bar.MainScreensBottomBar
import com.example.caloriecounter.main_screens.screens.MainScreensSharedVM
import com.example.caloriecounter.main_screens.screens.main_screens_bars.top_bar.MainScreensTopBar

@Composable
fun ActivityScreen(
    navController: NavHostController,
    mainScreensSharedVM: MainScreensSharedVM
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { MainScreensBottomBar(navController = navController) },
        topBar = { MainScreensTopBar(mainScreensSharedVM = mainScreensSharedVM) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {

        }
    }
}