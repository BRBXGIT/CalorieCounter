package com.example.caloriecounter.navigation_drawer_screens.nav_drawer_screens_top_bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.caloriecounter.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerScreensTopBar(
    navController: NavHostController,
    title: String,
    actions: @Composable RowScope.() -> Unit = {  }
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_navigation_arrow_left),
                    contentDescription = null
                )
            }
        },
        actions = actions
    )
}