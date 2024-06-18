package com.example.caloriecounter.main_screens.screens.main_screens_bars.bottom_bar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.caloriecounter.R
import com.example.caloriecounter.navigation.ActivityScreen
import com.example.caloriecounter.navigation.EatingScreen
import com.example.caloriecounter.navigation.HomeScreen

data class NavItem(
    val title: String,
    val defaultIcon: Int,
    val chosenIcon: Int,
    val route: Any,
    val destination: String
)

@Composable
fun MainScreensBottomBar(
    navController: NavHostController
) {
    val navItems = listOf(
        NavItem(
            title = "Home",
            defaultIcon = R.drawable.ic_home_outlined,
            chosenIcon = R.drawable.ic_home_filled,
            route = HomeScreen,
            destination = "HomeScreen"
        ),
        NavItem(
            title = "Eating",
            defaultIcon = R.drawable.ic_eating_outlined,
            chosenIcon = R.drawable.ic_eating_filled,
            route = EatingScreen,
            destination = "EatingScreen"
        ),
        NavItem(
            title = "Activity",
            defaultIcon = R.drawable.ic_run_outlined,
            chosenIcon = R.drawable.ic_run_filled,
            route = ActivityScreen,
            destination = "ActivityScreen"
        )
    )

    val currentDestination = navController.currentDestination?.route.toString().drop(38)
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.shadow(20.dp),
        tonalElevation = 0.dp,
    ) {
        navItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentDestination == navItem.destination,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                ),
                onClick = {
                    if(currentDestination != navItem.destination) {
                        navController.navigate(navItem.route)
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if(navItem.destination == currentDestination) navItem.chosenIcon else navItem.defaultIcon
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(text = navItem.title)
                }
            )
        }
    }
}