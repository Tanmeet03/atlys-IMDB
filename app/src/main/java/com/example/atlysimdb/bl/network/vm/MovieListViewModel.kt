package com.example.atlysimdb.bl.network.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MovieListState())
    val state: StateFlow<MovieListState> = _state.asStateFlow()

    private val _effect = Channel<MovieListEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        processIntent(MovieListIntent.LoadMovies)
    }

    fun processIntent(intent: MovieListIntent) {
        when (intent) {
            is MovieListIntent.LoadMovies -> processAction(MovieListAction.FetchMovies)
            is MovieListIntent.SearchMovies -> processAction(MovieListAction.SearchMovies(intent.query))
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
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false)
                _effect.send(MovieListEffect.ShowError("Failed to load movies: ${e.message}"))
            }
        }
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(searchQuery = query, isLoading = true, error = null)
            searchMoviesUseCase(query).collectLatest { movies ->
                _state.value = _state.value.copy(movies = movies, isLoading = false)
            }
        }
    }
}