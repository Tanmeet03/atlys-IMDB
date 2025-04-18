package com.example.atlysimdb.bl.network.vm

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atlysimdb.bl.network.domainData.Movie
import com.example.atlysimdb.bl.network.usecase.GetTrendingMoviesUseCase
import com.example.atlysimdb.bl.network.usecase.SearchMoviesUseCase
import com.example.atlysimdb.ui.list.action.MovieListAction
import com.example.atlysimdb.ui.list.effect.MovieListEffect
import com.example.atlysimdb.ui.list.intent.MovieListIntent
import com.example.atlysimdb.ui.list.state.MovieListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var lastQuery: String? = null

    private val _state = MutableStateFlow(
        MovieListState(
            lastQuery = savedStateHandle["searchQuery"] ?: "",
            movies = savedStateHandle["movies"] as? List<Movie> ?: emptyList()
        )
    )
    val state: StateFlow<MovieListState> = _state.asStateFlow()

    private val _effect = Channel<MovieListEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    var hasInitialLoad = savedStateHandle["hasInitialLoad"] as? Boolean ?: false

    fun processIntent(intent: MovieListIntent) {
        when (intent) {
            is MovieListIntent.LoadMovies -> {
                if (!hasInitialLoad) {
                    processAction(MovieListAction.FetchMovies)
                    hasInitialLoad = true
                    savedStateHandle["hasInitialLoad"] = true
                }
            }

            is MovieListIntent.SearchMovies -> {
                if (lastQuery != intent.query) {
                    processAction(MovieListAction.SearchMovies(intent.query))
                    savedStateHandle["searchQuery"] = intent.query
                } else {
                    Log.d(
                        "MovieListViewModel",
                        "Ignored duplicate search for query: '${intent.query}'"
                    )
                }
            }

            is MovieListIntent.SelectMovie -> viewModelScope.launch {
                _effect.send(MovieListEffect.NavigateToDetail(intent.movieId))
            }
        }
    }

    private fun processAction(action: MovieListAction) {
        when (action) {
            is MovieListAction.FetchMovies -> fetchMovies()
            is MovieListAction.SearchMovies -> searchMovies(action.query)
        }
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                getTrendingMoviesUseCase().collectLatest { movies ->
                    _state.value = _state.value.copy(movies = movies, isLoading = false)
                    savedStateHandle["movies"] = movies
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false)
                _effect.send(MovieListEffect.ShowError("Failed to load movies: ${e.message}"))
            }
        }
    }

    private fun searchMovies(query: String) {
        if (lastQuery == query) return

        lastQuery = query

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                searchMoviesUseCase(query).collectLatest { movies ->
                    _state.value = _state.value.copy(
                        movies = movies, isLoading = false, lastQuery = query
                    )
                    savedStateHandle["searchQuery"] = query
                    savedStateHandle["movies"] = movies
                }.runCatching {
                    if (this != null) {
                        _state.value = _state.value.copy(isLoading = false)
                        _effect.send(MovieListEffect.ShowError("No movies found"))
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false)
                _effect.send(MovieListEffect.ShowError("Failed to load movies: ${e.message}"))
            }

        }
    }

}
