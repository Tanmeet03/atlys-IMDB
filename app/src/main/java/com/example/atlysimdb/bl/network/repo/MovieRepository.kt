package com.example.atlysimdb.bl.network.repo

import androidx.multidex.BuildConfig
import com.example.atlysimdb.bl.cache.dao.MovieDao
import com.example.atlysimdb.bl.cache.entity.Movie
import com.example.atlysimdb.bl.network.api.MovieApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService, private val movieDao: MovieDao
) {
    suspend fun getMovies(): Flow<List<Movie>> = flow {
        try {
            val movies =
                movieApiService.getTrendingMovies(apiKey = BuildConfig.TMDB_API_KEY).results
            movieDao.insertMovies(movies)
            emit(movies)
        } catch (e: IOException) {
            movieDao.getMovies().collect { emit(it) }
        } catch (e: HttpException) {
            movieDao.getMovies().collect { emit(it) }
        }
    }

    fun searchMovies(query: String): Flow<List<Movie>> {
        return movieDao.searchMovies("%$query%")
    }
}