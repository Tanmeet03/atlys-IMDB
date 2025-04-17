package com.example.atlysimdb.bl.network.domainData

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val posterPath: String?
)