package com.example.atlysimdb.ui.detail

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.atlysimdb.bl.network.domainData.Movie
import com.example.atlysimdb.bl.network.vm.MovieDetailViewModel
import com.example.atlysimdb.ui.theme.CustomTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MovieDetailScreen(
    navController: NavController, viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MovieDetailEffect.NavigateBack -> {
                    navController.popBackStack()
                }

                is MovieDetailEffect.ShowError -> {
                    println("Error: ${effect.message}")
                }
            }
        }
    }

    BackHandler {
        viewModel.processIntent(MovieDetailIntent.BackPressed)
    }

    AnimatedContent(
        targetState = state,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "MovieDetailStateAnimation",
    ) { targetState ->
        when {
            targetState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            targetState.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = targetState.error, color = MaterialTheme.colorScheme.error)
                }
            }

            targetState.movie != null -> {
                MovieDetailContent(movie = targetState.movie, viewModel)
            }
        }
    }
}

@Composable
fun MovieDetailContent(movie: Movie, viewModel: MovieDetailViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 70.dp, start = 16.dp, end = 16.dp, bottom = 16.dp
                )
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = movie.title,
                style = CustomTheme.typography.titleLg,
                color = CustomTheme.colors.text
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = movie.overview,
                style = CustomTheme.typography.bodyMd,
                color = CustomTheme.colors.text
            )
        }


        IconButton(
            onClick = { viewModel.processIntent(MovieDetailIntent.BackPressed) },
            modifier = Modifier
                .padding(16.dp)
                .size(40.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}