package com.manishsputnikcorporation.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manishsputnikcorporation.movies.ui.screens.SplashScreen
import com.manishsputnikcorporation.movies.ui.screens.detail.MovieDetailScreen
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeScreen
import com.manishsputnikcorporation.movies.utils.navigateSingleTopTo

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreen.route,
        modifier = modifier
    ) {
        composable(route = SplashScreen.route) {
            SplashScreen() {
                navController.apply {
                    popBackStack()
                    navigateSingleTopTo(MoviesHome.route)
                }
            }
        }
        composable(route = MoviesHome.route) {
            MoviesHomeScreen(moviesHomeViewModel = hiltViewModel()) { movieId ->
                navController.navigateSingleTopTo("${MovieDetail.route}/$movieId")
            }
        }
        composable(
            route = MovieDetail.routeWithArgs,
            arguments = MovieDetail.arguments
        ) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getString(MovieDetail.movieIdArg)
            MovieDetailScreen(movieDetailViewModel = hiltViewModel(), movieId) {
                navController.popBackStack()
            }
        }
    }
}