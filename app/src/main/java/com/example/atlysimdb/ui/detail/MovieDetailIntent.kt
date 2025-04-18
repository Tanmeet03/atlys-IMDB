package com.example.atlysimdb.ui.detail

sealed class MovieDetailIntent {
    data class LoadMovie(val movieId: Int) : MovieDetailIntent()
    object BackPressed : MovieDetailIntent()
}