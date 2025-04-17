package com.example.atlysimdb.bl.network.usecase

import com.example.atlysimdb.bl.network.domainData.Movie
import com.example.atlysimdb.bl.network.repo.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(query: String): Flow<List<Movie>> {
        return repository.searchMovies(query)
    }
}