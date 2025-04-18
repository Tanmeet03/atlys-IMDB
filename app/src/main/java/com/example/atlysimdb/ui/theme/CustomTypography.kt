package com.example.atlysimdb.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp


data class CustomTypography(
    val titleMd: TextStyle = TextStyle(
        fontFamily = FontFamily.inter,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        fontSize = 16.sp,
        letterSpacing = TextUnit(-0.40F, TextUnitType.Sp)
    ),

    val bodyMd: TextStyle = TextStyle(
        fontFamily = FontFamily.inter,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        fontSize = 16.sp,
        letterSpacing = TextUnit(-0.08F, TextUnitType.Sp)
    )
)

val LocalTypography = staticCompositionLocalOf { CustomTypography() }