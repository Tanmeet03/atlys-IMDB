package com.example.atlysimdb.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.atlysimdb.ui.detail.MovieDetailScreen
import com.example.atlysimdb.ui.list.screen.MovieListScreen
import com.example.atlysimdb.ui.setting.SettingsScreen
import com.example.atlysimdb.ui.theme.ThemePreference

@Composable
fun MovieAppNavGraph(themePreference: ThemePreference) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "movie_list") {
        composable("movie_list") {
            MovieListScreen(navController = navController)
        }
        composable(
            route = "movie_detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            MovieDetailScreen(navController = navController)
        }
        composable("settings") {
            SettingsScreen(navController = navController, themePreference = themePreference)
        }
    }
}