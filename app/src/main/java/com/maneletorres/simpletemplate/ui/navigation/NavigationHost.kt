package com.maneletorres.simpletemplate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maneletorres.simpletemplate.ui.detail.MovieDetailScreen
import com.maneletorres.simpletemplate.ui.detail.MovieDetailViewModel
import com.maneletorres.simpletemplate.ui.home.MoviesHomeScreen
import com.maneletorres.simpletemplate.ui.home.MoviesHomeViewModel

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            MoviesHomeScreen(moviesHomeViewModel = viewModel(), /* TODO: navController */)
        }
        composable("details") {
            MovieDetailScreen(movieDetailViewModel = viewModel())
        }
    }
}