package com.example.atlysimdb.ui.list.intent

sealed class MovieListIntent {
    object LoadMovies : MovieListIntent()
    data class SearchMovies(val query: String) : MovieListIntent()
    data class SelectMovie(val movieId: Int) : MovieListIntent()
}