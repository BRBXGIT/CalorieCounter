package com.example.caloriecounter.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
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
    surface = Color(0xff22222d),
    surfaceVariant = Color(0xff272634),
    primary = Color(0xff4ddc9b),
    onPrimary = Color(0xfff7f7fb),
    secondary = Color(0xff6bd7e5),
    onBackground = Color(0xffa2a2ac),
    onSurface = Color(0xffd8d8da),
    onSurfaceVariant = Color(0xff77777c),
    surfaceDim = Color(0xff32313e),
    surfaceBright = Color(0xffb8b8b9),
    error = Color(0xfffb8989),
    primaryContainer = Color(0xff77777c)
)

private val LightColorScheme = lightColorScheme(
    background = Color(0xfff7f7fb),
    surface = Color(0xffeff0f4),
    surfaceVariant = Color(0xffffffff),
    primary = Color(0xff4ddc9b),
    onPrimary = Color(0xff51525b),
    secondary = Color(0xff6bd7e5),
    onBackground = Color(0xff51525b),
    onSurface = Color(0xff373546),
    onSurfaceVariant = Color(0xffb5b5bb),
    surfaceDim = Color(0xfff3f5f9),
    surfaceBright = Color(0xffb8b8b9),
    error = Color(0xfffb8989),
    primaryContainer = Color(0xffeef5f4)
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun CalorieCounterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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