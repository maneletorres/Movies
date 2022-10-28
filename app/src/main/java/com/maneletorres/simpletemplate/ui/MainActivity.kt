package com.maneletorres.simpletemplate.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maneletorres.simpletemplate.ui.detail.MovieDetailScreen
import com.maneletorres.simpletemplate.ui.home.MoviesHomeScreen
import com.maneletorres.simpletemplate.ui.navigation.MovieDetail
import com.maneletorres.simpletemplate.ui.navigation.MoviesHome
import com.maneletorres.simpletemplate.ui.theme.SimpleTemplateTheme
import com.maneletorres.simpletemplate.utils.navigateSingleTopTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoviesApp()
        }
    }
}

@Composable
fun MoviesApp() {
    SimpleTemplateTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = MoviesHome.route,
            modifier = Modifier
        ) {
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
                MovieDetailScreen(movieDetailViewModel = hiltViewModel(), movieId)
            }
        }
    }
}