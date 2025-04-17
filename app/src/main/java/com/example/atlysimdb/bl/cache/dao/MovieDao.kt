package com.example.atlysimdb.bl.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.atlysimdb.bl.cache.entity.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    fun getMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movies WHERE title LIKE :query")
    fun searchMovies(query: String): Flow<List<Movie>>
}