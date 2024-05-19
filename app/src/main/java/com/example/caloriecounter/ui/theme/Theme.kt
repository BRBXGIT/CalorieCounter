package com.example.caloriecounter.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.example.caloriecounter.MainActivity

private val DarkColorScheme = darkColorScheme(
    background = Color(0xff1a1920),
    surface = Color(0xff232431),
    primary = Color(0xff4ddc9b),
    onPrimary = Color(0xfff7f7fb),
    secondary = Color(0xff6bd7e5),
    onBackground = Color(0xffa2a2ac),
    onSurface = Color(0xffd8d8da),
    tertiary = Color(0xfff9df80),
    error = Color(0xfffb8989)
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun CalorieCounterTheme(
    darkTheme: Boolean = true,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    activity: Activity = LocalContext.current as MainActivity,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val window = calculateWindowSizeClass(activity = activity)
    val config = LocalConfiguration.current

    var appDimens = compactMediumDimens

    when(window.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            appDimens = if((config.screenHeightDp) <= 640) {
                compactSmallDimens
            } else if((config.screenHeightDp) <= 800) {
                compactSmallMediumDimens
            } else if((config.screenHeightDp) <= 920) {
                compactMediumDimens
            } else {
                compactLargeDimens
            }
        }
    }

    AppUtils(appDimens = appDimens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

val MaterialTheme.dimens
    @Composable
    get() = LocalAppDimens.current