package com.example.atlysimdb.di

import android.content.Context
import androidx.room.Room
import com.example.atlysimdb.bl.cache.dao.MovieDao
import com.example.atlysimdb.bl.cache.db.MovieDatabase
import com.example.atlysimdb.bl.cache.mapper.MovieMapper
import com.example.atlysimdb.bl.cache.mapper.MovieMapperImpl
import com.example.atlysimdb.bl.network.api.MovieApiService
import com.example.atlysimdb.bl.network.repo.MovieRepository
import com.example.atlysimdb.bl.network.usecase.GetTrendingMoviesUseCase
import com.example.atlysimdb.bl.network.usecase.SearchMoviesUseCase
import com.example.atlysimdb.ui.theme.ThemePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMovieApiService(): MovieApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideThemePreference(@ApplicationContext context: Context): ThemePreference {
        return ThemePreference(context)
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()

    @Provides
    @Singleton
    fun provideMovieMapper(): MovieMapper = MovieMapperImpl()

    @Provides
    @Singleton
    fun provideMovieRepository(
        apiService: MovieApiService,
        movieDao: MovieDao,
        mapper: MovieMapper
    ): MovieRepository {
        return MovieRepository(apiService, movieDao, mapper)
    }

    @Provides
    @Singleton
    fun provideGetTrendingMoviesUseCase(repository: MovieRepository): GetTrendingMoviesUseCase {
        return GetTrendingMoviesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchMoviesUseCase(repository: MovieRepository): SearchMoviesUseCase {
        return SearchMoviesUseCase(repository)
    }
}