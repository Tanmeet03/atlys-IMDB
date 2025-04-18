package com.example.atlysimdb.ui.detail

import com.example.atlysimdb.bl.network.domainData.Movie

data class MovieDetailState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)