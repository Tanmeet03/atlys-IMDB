package com.example.atlysimdb.ui.list.state

import com.example.atlysimdb.bl.network.domainData.Movie

data class MovieListState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)