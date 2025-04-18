package com.example.atlysimdb.ui.detail

sealed class MovieDetailEffect {
    object NavigateBack : MovieDetailEffect()
    data class ShowError(val message: String) : MovieDetailEffect()
}