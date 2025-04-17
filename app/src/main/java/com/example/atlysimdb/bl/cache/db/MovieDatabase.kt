package com.example.atlysimdb.bl.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.atlysimdb.bl.cache.dao.MovieDao
import com.example.atlysimdb.bl.cache.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}