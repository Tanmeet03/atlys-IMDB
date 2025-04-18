package com.example.atlysimdb.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class CustomColors(
    surface: Color, gray200: Color, black: Color, gray400: Color, white: Color, isLight: Boolean
) {
    var Gray200 by mutableStateOf(gray200)
        private set
    var text by mutableStateOf(black)
        private set
    var Gray400 by mutableStateOf(gray400)
        private set
    var White by mutableStateOf(white)
        private set
    var Surface by mutableStateOf(surface)
        private set
    var isLight by mutableStateOf(isLight)
        private set

    fun updateColors(
        surface: Color = this.Surface,
        gray200: Color = this.Gray200,
        black: Color = this.text,
        gray400: Color = this.Gray400,
        white: Color = this.White,
        isLight: Boolean = this.isLight
    ) {
        this.Surface = surface
        this.Gray200 = gray200
        this.text = black
        this.Gray400 = gray400
        this.White = white
        this.isLight = isLight
    }
}

val LocalColors = staticCompositionLocalOf { lightColors() }