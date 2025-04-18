package com.example.atlysimdb.bl.cache.mapper

import com.example.atlysimdb.bl.cache.entity.MovieEntity
import com.example.atlysimdb.bl.network.domainData.Movie
import com.example.atlysimdb.bl.network.domainData.NetworkMovie
import javax.inject.Inject

interface MovieMapper {
    fun networkToEntity(networkMovie: NetworkMovie): MovieEntity
    fun entityToDomain(movieEntity: MovieEntity): Movie
    fun networkToDomain(networkMovie: NetworkMovie): Movie
    fun entitiesToDomain(movieEntities: List<MovieEntity>): List<Movie>
    fun networksToDomain(networkMovies: List<NetworkMovie>): List<Movie>
}

class MovieMapperImpl @Inject constructor() : MovieMapper {
    override fun networkToEntity(networkMovie: NetworkMovie): MovieEntity {
        return MovieEntity(
            id = networkMovie.id,
            title = networkMovie.title,
            overview = networkMovie.overview,
            releaseDate = networkMovie.releaseDate,
            voteAverage = networkMovie.voteAverage,
            posterPath = networkMovie.posterPath
        )
    }

    override fun entityToDomain(movieEntity: MovieEntity): Movie {
        return Movie(
            id = movieEntity.id,
            title = movieEntity.title,
            overview = movieEntity.overview,
            releaseDate = movieEntity.releaseDate,
            voteAverage = movieEntity.voteAverage,
            posterPath = movieEntity.posterPath ?: ""
        )
    }

    override fun networkToDomain(networkMovie: NetworkMovie): Movie {
        return Movie(
            id = networkMovie.id,
            title = networkMovie.title,
            overview = networkMovie.overview,
            releaseDate = networkMovie.releaseDate,
            voteAverage = networkMovie.voteAverage,
            posterPath = networkMovie.posterPath ?: ""
        )
    }

    override fun entitiesToDomain(movieEntities: List<MovieEntity>): List<Movie> {
        return movieEntities.map { entityToDomain(it) }
    }

    override fun networksToDomain(networkMovies: List<NetworkMovie>): List<Movie> {
        return networkMovies.map { networkToDomain(it) }
    }
}