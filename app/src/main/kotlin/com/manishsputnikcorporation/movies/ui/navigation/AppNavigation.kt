package com.manishsputnikcorporation.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manishsputnikcorporation.movies.ui.screens.auth.LoginScreen
import com.manishsputnikcorporation.movies.ui.screens.auth.RecoverPasswordScreen
import com.manishsputnikcorporation.movies.ui.screens.auth.SignInScreen
import com.manishsputnikcorporation.movies.ui.screens.detail.MovieDetailScreen
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeScreen
import com.manishsputnikcorporation.movies.ui.screens.splash.SplashScreen
import com.manishsputnikcorporation.movies.utils.extensions.navigateInclusiveTo
import com.manishsputnikcorporation.movies.utils.extensions.navigateSingleTopTo
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.manager.AuthManager

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    val analyticsManager = AnalyticsManager(LocalContext.current)
    val authManager = AuthManager()
    val currentUser = authManager.getCurrentUser()
    val startDestination = if (currentUser != null) MoviesHome.route else SplashScreen.route

    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable(route = SplashScreen.route) {
            SplashScreen(analyticsManager = analyticsManager) {
                navController.apply {
                    popBackStack()
                    navigateSingleTopTo(LoginScreen.route)
                }
            }
        }

        // region Auth screens:
        composable(route = LoginScreen.route) {
            LoginScreen(analyticsManager = analyticsManager, authManager = authManager) { nextScreen, previousScreen ->
                navController.navigateInclusiveTo(nextScreen, previousScreen)
            }
        }
        composable(route = SignInScreen.route) { SignInScreen(analyticsManager = analyticsManager) { navController.popBackStack() } }
        composable(route = RecoverPasswordScreen.route) { RecoverPasswordScreen() }
        // endregion

        composable(route = MoviesHome.route) {
            MoviesHomeScreen(
                analyticsManager = analyticsManager,
                moviesHomeViewModel = hiltViewModel(),
                onClickSignOut = {
                    authManager.signOut()
                    navController.navigateInclusiveTo(LoginScreen.route, MoviesHome.route)
                },
                onClickMovie = { movieId -> navController.navigateSingleTopTo("${MovieDetail.route}/$movieId") }
            )
        }
        composable(route = MovieDetail.routeWithArgs, arguments = MovieDetail.arguments) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getString(MovieDetail.movieIdArg)
            MovieDetailScreen(analyticsManager = analyticsManager, movieDetailViewModel = hiltViewModel(), movieId) {
                navController.popBackStack()
            }
        }
    }
}
