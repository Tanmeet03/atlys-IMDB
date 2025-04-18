package com.example.atlysimdb.ui.theme

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun AtlysIMDBTheme(
    spaces: CustomSpaces = CustomTheme.spaces,
    typography: CustomTypography = CustomTheme.typography,
    colors: CustomColors = lightColors(),
    darkColors: CustomColors = darkColors(),
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit,
) {
    val isDarkTheme = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    val currentColor = remember(themeMode) {
        if (isDarkTheme) darkColors else colors
    }.apply {
        updateColors(
            surface = if (isDarkTheme) darkColors.Surface else colors.Surface,
            gray200 = if (isDarkTheme) darkColors.Gray200 else colors.Gray200,
            black = if (isDarkTheme) darkColors.text else colors.text,
            gray400 = if (isDarkTheme) darkColors.Gray400 else colors.Gray400,
            white = if (isDarkTheme) darkColors.White else colors.White,
            isLight = !isDarkTheme
        )
    }

    CompositionLocalProvider(
        LocalColors provides currentColor,
        LocalSpaces provides spaces,
        LocalTypography provides typography,
    ) {
        ProvideTextStyle(typography.bodyMd.copy(color = CustomTheme.colors.Gray200), content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = CustomTheme.colors.Surface
            ) {
                content()
            }
        })
    }
}