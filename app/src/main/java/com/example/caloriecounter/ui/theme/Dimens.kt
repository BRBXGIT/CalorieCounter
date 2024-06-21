package com.example.caloriecounter.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val authScreensSpacer: Dp,
    val signInScreenBottomSpacer: Dp,
    val profileScreenSpacer: Dp
)

val compactSmallDimens = Dimens(
    authScreensSpacer = 16.dp,
    signInScreenBottomSpacer = 0.dp,
    profileScreenSpacer = 16.dp
) //Small phone

val compactSmallMediumDimens = Dimens(
    authScreensSpacer = 24.dp,
    signInScreenBottomSpacer = 56.dp,
    profileScreenSpacer = 32.dp
) //Samsung s20fe

val compactMediumDimens = Dimens(
    authScreensSpacer = 32.dp,
    signInScreenBottomSpacer = 64.dp,
    profileScreenSpacer = 32.dp
) //Pixel 8

val compactLargeDimens = Dimens(
    authScreensSpacer = 32.dp,
    signInScreenBottomSpacer = 64.dp,
    profileScreenSpacer = 32.dp
) //Pixel 8 pro
