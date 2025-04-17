package com.example.atlysimdb.bl.network.repo

import com.example.atlysimdb.bl.cache.dao.MovieDao
import com.example.atlysimdb.bl.cache.mapper.MovieMapper
import com.example.atlysimdb.bl.network.api.MovieApiService
import com.example.atlysimdb.bl.network.domainData.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao,
    private val movieMapper: MovieMapper
) {
    suspend fun getMovies(): Flow<List<Movie>> = flow {
        try {
            val networkMovies =
                movieApiService.getTrendingMovies(apiKey = "5038c20449c90b274d18da63e34254ec").results
            val entities = networkMovies.map { movieMapper.networkToEntity(it) }
            movieDao.insertMovies(entities)
            emit(movieMapper.networksToDomain(networkMovies))
        } catch (e: IOException) {
            movieDao.getMovies().collect { entities ->
                emit(movieMapper.entitiesToDomain(entities))
            }
        } catch (e: HttpException) {
            movieDao.getMovies().collect { entities ->
                emit(movieMapper.entitiesToDomain(entities))
            }
        }
    }

    fun searchMovies(query: String): Flow<List<Movie>> {
        return movieDao.searchMovies("%$query%").map { entities ->
            movieMapper.entitiesToDomain(entities)
        }
    }
}