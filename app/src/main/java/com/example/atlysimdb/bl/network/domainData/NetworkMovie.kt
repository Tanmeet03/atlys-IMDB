package com.example.atlysimdb.bl.network.domainData

import com.google.gson.annotations.SerializedName

data class NetworkMovie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") val posterPath: String?
)

data class NetworkMovieResponse(
    @SerializedName("results") val results: List<NetworkMovie>
)