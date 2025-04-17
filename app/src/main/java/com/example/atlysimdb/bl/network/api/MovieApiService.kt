package com.example.atlysimdb.bl.network.api

import com.example.atlysimdb.bl.network.domainData.NetworkMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("trending/movie/week?language=en-US")
    suspend fun getTrendingMovies(@Query("api_key") apiKey: String): NetworkMovieResponse
}