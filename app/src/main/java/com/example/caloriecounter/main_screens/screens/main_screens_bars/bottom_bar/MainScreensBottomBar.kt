package com.example.caloriecounter.main_screens.presentation.main_screens_bars.bottom_bar

import android.util.Log
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
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
    val route: Any
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
            route = HomeScreen
        ),
        NavItem(
            title = "Eating",
            defaultIcon = R.drawable.ic_eating_outlined,
            chosenIcon = R.drawable.ic_eating_filled,
            route = EatingScreen
        ),
        NavItem(
            title = "Activity",
            defaultIcon = R.drawable.ic_run_outlined,
            chosenIcon = R.drawable.ic_run_filled,
            route = ActivityScreen
        )
    )

    val currentDestination = navController.currentDestination?.route
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .shadow(20.dp)
            .height(74.dp),
        tonalElevation = 0.dp,
    ) {
        navItems.forEach { navItem ->
            NavigationBarItem(
                //Drop last(8) dropping hashcode of route
                selected = currentDestination == navItem.route.toString().dropLast(8),
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = Color.Transparent
                ),
                onClick = {
                    if(navItem.route.toString().dropLast(8) != currentDestination) {
                        navController.navigate(navItem.route)
                    }
                },
                icon = {
                    if(navItem.route.toString().dropLast(8) == currentDestination) {
                        Log.d("XXXX", "true")
                        Icon(
                            painter = painterResource(id = navItem.chosenIcon),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = navItem.defaultIcon),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                label = {
                    Text(text = navItem.title)
                }
            )
        }
    }
}