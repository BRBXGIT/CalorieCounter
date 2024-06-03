package com.example.caloriecounter.main_screens.screens.eating_screen.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.screens.MainScreensSharedVM
import com.example.caloriecounter.main_screens.screens.main_screens_bars.bottom_bar.MainScreensBottomBar
import com.example.caloriecounter.main_screens.screens.main_screens_bars.top_bar.MainScreensTopBar
import com.example.caloriecounter.navigation.AddDishScreen
import com.example.caloriecounter.navigation.DishesScreen

@Composable
fun EatingScreen(
    navController: NavHostController,
    mainScreensSharedVM: MainScreensSharedVM,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { MainScreensBottomBar(navController = navController) },
        topBar = { MainScreensTopBar(mainScreensSharedVM = mainScreensSharedVM) }
    ) { innerPadding ->
        val mealsList = listOf(
            "Breakfast",
            "Lunch",
            "Dinner",
            "Snack"
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    top = innerPadding.calculateTopPadding() + 32.dp,
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(mealsList) { meal ->
                Surface(
                    onClick = {
                        navController.navigate(DishesScreen(dishType = meal))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .animateContentSize(),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = meal
                            )

                            IconButton(
                                onClick = { navController.navigate(AddDishScreen(
                                    dishType = meal
                                )) },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_plus),
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}