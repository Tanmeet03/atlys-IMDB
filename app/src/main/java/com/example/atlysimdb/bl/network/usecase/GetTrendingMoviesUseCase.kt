package com.example.atlysimdb.bl.network.usecase

import com.example.atlysimdb.bl.network.domainData.Movie
import com.example.atlysimdb.bl.network.repo.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): Flow<List<Movie>> {
        return repository.getMovies()
    }
}