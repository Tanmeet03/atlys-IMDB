package com.example.atlysimdb.ui.theme

import androidx.compose.ui.graphics.Color

val Gray200 = Color(0xFFE3E8EF)
val Black = Color(0xFF000000)
val Gray400 = Color(0xFFE3E8EF)
val White = Color(0xFFFFFFFF)
val darkSurface = Color(0xFF222222)
val darkBackground = Color(0xFF121212)


fun lightColors() = CustomColors(
    surface = White,
    gray200 = Gray200,
    black = Black,
    gray400 = Gray400,
    white = White,
    isLight = true,
)

fun darkColors() = CustomColors(
    surface = darkBackground,
    gray200 = Gray200,
    black = White,
    gray400 = Gray400,
    white = darkSurface,
    isLight = false,
)