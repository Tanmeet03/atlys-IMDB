package com.example.atlysimdb.ui.list.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.atlysimdb.bl.network.vm.MovieListViewModel
import com.example.atlysimdb.ui.list.components.CustomSearchBar
import com.example.atlysimdb.ui.list.components.MovieItem
import com.example.atlysimdb.ui.list.effect.MovieListEffect
import com.example.atlysimdb.ui.list.intent.MovieListIntent
import com.example.atlysimdb.ui.theme.CustomTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MovieListScreen(
    navController: NavController, viewModel: MovieListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    var searchQuery by remember { mutableStateOf("") }
    var hasPrefilledQuery by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val isLandscape =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
    val gridColumns = if (isLandscape) 4 else 2

    LaunchedEffect(Unit) {
        if (!hasPrefilledQuery) {
            searchQuery = state.lastQuery
            hasPrefilledQuery = true
        }
    }

    LaunchedEffect(searchQuery) {
        delay(400)
        if (searchQuery != state.lastQuery) {
            viewModel.processIntent(MovieListIntent.SearchMovies(searchQuery))
        }
    }

    LaunchedEffect(Unit) {
        if (state.movies.isEmpty() && !viewModel.hasInitialLoad) {
            viewModel.processIntent(MovieListIntent.LoadMovies)
        }
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MovieListEffect.NavigateToDetail -> {
                    navController.navigate("movie_detail/${effect.movieId}")
                }

                is MovieListEffect.ShowError -> {
                    println("Error: ${effect.message}")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.Surface)
            .padding(16.dp)
            .systemBarsPadding()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CustomSearchBar(
                value = searchQuery, onValueChange = {
                    searchQuery = it
                })

            IconButton(
                modifier = Modifier.size(40.dp), onClick = { navController.navigate("settings") }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedContent(
            targetState = state,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "MovieListStateAnimation"
        ) { targetState ->
            when {
                targetState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                targetState.error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = targetState.error, color = CustomTheme.colors.Gray400)
                    }
                }

                targetState.movies.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No movies found")
                    }
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(gridColumns),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(targetState.movies.size) { index ->
                            val movie = targetState.movies[index]
                            MovieItem(movie = movie) {
                                viewModel.processIntent(MovieListIntent.SelectMovie(movie.id))
                            }
                        }
                    }
                }
            }
        }
    }
}