package com.example.caloriecounter.navigation_drawer_screens.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.caloriecounter.app.data.preferences_data_store.PreferencesDataStoreManager
import com.example.caloriecounter.navigation_drawer_screens.nav_drawer_screens_top_bar.NavigationDrawerScreensTopBar

data class ThemePreview(
    val name: String,
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
)

@Composable
fun SettingsScreen(
    preferencesDataStoreManager: PreferencesDataStoreManager,
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            NavigationDrawerScreensTopBar(
                navController = navController,
                title = "Settings"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Spacer(modifier = Modifier.height(0.dp))

            val themesList = listOf(
                ThemePreview(
                    name = "LightTheme",
                    background = Color(0xfff7f7fb),
                    surface = Color(0xffeff0f4),
                    surfaceVariant = Color(0xffffffff),
                    primary = Color(0xff4ddc9b),
                    onPrimary = Color(0xff51525b),
                    secondary = Color(0xff6bd7e5),
                    onBackground = Color(0xff51525b),
                    onSurface = Color(0xff373546),
                    onSurfaceVariant = Color(0xffb5b5bb),
                ),
                ThemePreview(
                    name = "DarkTheme",
                    background = Color(0xff1a1920),
                    surface = Color(0xff22222d),
                    surfaceVariant = Color(0xff272634),
                    primary = Color(0xff4ddc9b),
                    onPrimary = Color(0xfff7f7fb),
                    secondary = Color(0xff6bd7e5),
                    onBackground = Color(0xffa2a2ac),
                    onSurface = Color(0xffd8d8da),
                    onSurfaceVariant = Color(0xff77777c),
                ),
                ThemePreview(
                    name = "SakuraLight",
                    background = Color(0xfff7f7fb),
                    surface = Color(0xffeff0f4),
                    surfaceVariant = Color(0xffffffff),
                    primary = Color(0xffdfb0b6),
                    onPrimary = Color(0xff51525b),
                    secondary = Color(0xffbf8065),
                    onBackground = Color(0xff51525b),
                    onSurface = Color(0xff373546),
                    onSurfaceVariant = Color(0xffb5b5bb),
                ),
                ThemePreview(
                    name = "SakuraDark",
                    background = Color(0xff1a1920),
                    surface = Color(0xff22222d),
                    surfaceVariant = Color(0xff272634),
                    primary = Color(0xffdfb0b6),
                    onPrimary = Color(0xfff7f7fb),
                    secondary = Color(0xffbf8065),
                    onBackground = Color(0xffa2a2ac),
                    onSurface = Color(0xffd8d8da),
                    onSurfaceVariant = Color(0xff77777c),
                )
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Color theme",
                    modifier = Modifier.padding(start = 14.dp)
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp
                    )
                ) {
                    items(themesList) { theme ->
                        Box(
                            modifier = Modifier
                                .height(200.dp)
                                .width(120.dp)
                                .background(
                                    color = theme.background,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .border(
                                    width = 3.dp,
                                    shape = RoundedCornerShape(10.dp),
                                    color = theme.surface
                                )
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(30.dp)
                                        .background(
                                            color = theme.surface,
                                            shape = RoundedCornerShape(
                                                topEnd = 10.dp,
                                                topStart = 10.dp
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.5f)
                                            .height(15.dp)
                                            .clip(RoundedCornerShape(30.dp))
                                            .background(theme.onSurface)
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp)
                                ) {
                                    items(3) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(20.dp)
                                                .background(
                                                    color = theme.surfaceVariant,
                                                    shape = RoundedCornerShape(4.dp)
                                                )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .height(10.dp)
                                                    .width(40.dp)
                                                    .padding(start = 4.dp)
                                                    .background(
                                                        color = theme.onSurfaceVariant,
                                                        shape = RoundedCornerShape(30.dp)
                                                    )
                                                    .align(Alignment.CenterStart)
                                            )

                                            Box(
                                                modifier = Modifier
                                                    .padding(end = 4.dp)
                                                    .size(10.dp)
                                                    .background(
                                                        color = theme.onSurfaceVariant,
                                                        shape = CircleShape
                                                    )
                                                    .align(Alignment.CenterEnd)
                                            ) {

                                            }
                                        }
                                    }
                                }
                                
                                Spacer(modifier = Modifier.weight(1f))
                                
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(30.dp)
                                        .background(
                                            color = theme.surface,
                                            shape = RoundedCornerShape(
                                                bottomEnd = 10.dp,
                                                bottomStart = 10.dp
                                            )
                                        )
                                ) {
                                    LazyRow(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceAround,
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        item {
                                            Box(
                                                modifier = Modifier
                                                    .size(15.dp)
                                                    .background(
                                                        color = theme.primary,
                                                        shape = CircleShape
                                                    )
                                            )
                                        }

                                        items(2) {
                                            Box(
                                                modifier = Modifier
                                                    .size(15.dp)
                                                    .background(
                                                        color = theme.onSurfaceVariant,
                                                        shape = CircleShape
                                                    )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}