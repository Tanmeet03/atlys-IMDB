package com.example.atlysimdb.bl.network.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atlysimdb.bl.network.usecase.GetTrendingMoviesUseCase
import com.example.atlysimdb.ui.detail.MovieDetailAction
import com.example.atlysimdb.ui.detail.MovieDetailEffect
import com.example.atlysimdb.ui.detail.MovieDetailIntent
import com.example.atlysimdb.ui.detail.MovieDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()

    private val _effect = Channel<MovieDetailEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        val movieId: Int? = savedStateHandle["movieId"]
        movieId?.let { processIntent(MovieDetailIntent.LoadMovie(it)) }
    }

    fun processIntent(intent: MovieDetailIntent) {
        when (intent) {
            is MovieDetailIntent.LoadMovie -> processAction(MovieDetailAction.LoadMovie(intent.movieId))
            is MovieDetailIntent.BackPressed -> viewModelScope.launch {
                _effect.send(MovieDetailEffect.NavigateBack)
            }
        }
    }

    private fun processAction(action: MovieDetailAction) {
        when (action) {
            is MovieDetailAction.LoadMovie -> loadMovie(action.movieId)
        }
    }

    private fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                getTrendingMoviesUseCase().collect { movies ->
                    val movie = movies.find { it.id == movieId }
                    _state.value = _state.value.copy(movie = movie, isLoading = false)
                    if (movie == null) {
                        _effect.send(MovieDetailEffect.ShowError("Movie not found"))
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false)
                _effect.send(MovieDetailEffect.ShowError("Failed to load movie: ${e.message}"))
            }
        }
    }
}