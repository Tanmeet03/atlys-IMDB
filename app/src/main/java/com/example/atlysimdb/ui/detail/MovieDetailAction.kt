package com.example.atlysimdb.ui.detail

sealed class MovieDetailAction {
    data class LoadMovie(val movieId: Int) : MovieDetailAction()
}