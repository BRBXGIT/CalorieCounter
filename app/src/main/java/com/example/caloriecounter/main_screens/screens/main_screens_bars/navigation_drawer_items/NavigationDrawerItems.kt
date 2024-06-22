package com.example.caloriecounter.main_screens.screens.main_screens_bars.navigation_drawer_items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.caloriecounter.R
import com.example.caloriecounter.navigation.CalculationsScreen
import com.example.caloriecounter.navigation.InfoScreen
import com.example.caloriecounter.navigation.MealTimeScreen
import com.example.caloriecounter.navigation.ProfileScreen
import com.example.caloriecounter.navigation.SettingsScreen

@Composable
fun NavigationDrawerItems(
    navController: NavHostController
) {
    NavigationDrawerItem(
        label = { Text(
            text = "My profile",
            fontSize = 16.sp
        ) },
        selected = false,
        onClick = { navController.navigate(ProfileScreen) },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = null
            )
        },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            unselectedTextColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(0.dp)
    )
    NavigationDrawerItem(
        label = { Text(
            text = "Calculations",
            fontSize = 16.sp
        ) },
        selected = false,
        onClick = { navController.navigate(CalculationsScreen) },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_calculator),
                contentDescription = null
            )
        },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            unselectedTextColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(0.dp)
    )
    NavigationDrawerItem(
        label = { Text(
            text = "Meal time",
            fontSize = 16.sp
        ) },
        selected = false,
        onClick = { navController.navigate(MealTimeScreen) },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_stopwatch),
                contentDescription = null
            )
        },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            unselectedTextColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(0.dp)
    )
    NavigationDrawerItem(
        label = { Text(
            text = "Info",
            fontSize = 16.sp
        ) },
        selected = false,
        onClick = { navController.navigate(InfoScreen) },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = null
            )
        },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            unselectedTextColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(0.dp)
    )
    NavigationDrawerItem(
        label = { Text(
            text = "Settings",
            fontSize = 16.sp
        ) },
        selected = false,
        onClick = { navController.navigate(SettingsScreen) },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = null
            )
        },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            unselectedTextColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(0.dp)
    )
}