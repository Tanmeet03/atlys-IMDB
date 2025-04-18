package com.example.atlysimdb.ui.list.screen

import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.atlysimdb.bl.network.vm.MovieListViewModel
import com.example.atlysimdb.ui.list.components.CustomSearchBar
import com.example.atlysimdb.ui.list.components.MovieItem
import com.example.atlysimdb.ui.list.effect.MovieListEffect
import com.example.atlysimdb.ui.list.intent.MovieListIntent
import com.example.atlysimdb.ui.theme.CustomTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MovieListEffect.NavigateToDetail -> {
                    Log.i("tanmeetss","processIntent - ${effect.movieId}")
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
                value = state.searchQuery,
                onValueChange = { viewModel.processIntent(MovieListIntent.SearchMovies(it)) })

            IconButton(
                modifier = Modifier.size(40.dp), onClick = { navController.navigate("settings") }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp) // standard icon size
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
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(targetState.movies.size) { index ->
                            val movie = targetState.movies[index]
                            MovieItem(movie = movie) {
                                Log.i("tanmeetss","id - ${movie.id}")
                                viewModel.processIntent(MovieListIntent.SelectMovie(movie.id))
                            }
                        }
                    }
                }
            }
        }
    }
}
