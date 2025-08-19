package com.manishsputnikcorporation.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manishsputnikcorporation.movies.ui.MainViewModel
import com.manishsputnikcorporation.movies.ui.screens.auth.login.LoginScreen
import com.manishsputnikcorporation.movies.ui.screens.auth.passwordRecovery.PasswordRecoveryScreen
import com.manishsputnikcorporation.movies.ui.screens.auth.signUp.SignUpScreen
import com.manishsputnikcorporation.movies.ui.screens.detail.MovieDetailScreen
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeScreen
import com.manishsputnikcorporation.movies.ui.screens.splash.SplashScreen
import com.manishsputnikcorporation.movies.utils.extensions.navigateInclusiveTo
import com.manishsputnikcorporation.movies.utils.extensions.navigateSingleTopTo

@Composable
fun AppNavigation(mainViewModel: MainViewModel, navController: NavHostController, modifier: Modifier = Modifier) {
    val currentUser = mainViewModel.getCurrentUser()
    val startDestination = if (currentUser != null) MoviesHome.route else SplashScreen.route

    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable(route = SplashScreen.route) {
            SplashScreen(splashViewModel = hiltViewModel()) {
                navController.apply {
                    popBackStack()
                    navigateSingleTopTo(route = LoginScreen.route)
                }
            }
        }

        // region Auth screens:
        composable(route = LoginScreen.route) {
            LoginScreen(loginViewModel = hiltViewModel()) { nextScreen, previousScreen ->
                navController.navigateInclusiveTo(nextScreenRoute = nextScreen, previousScreenRoute = previousScreen)
            }
        }
        composable(route = SignUpScreen.route) {
            SignUpScreen(signUpViewModel = hiltViewModel()) {
                navController.navigateInclusiveTo(nextScreenRoute = MoviesHome.route, previousScreenRoute = LoginScreen.route)
            }
        }
        composable(route = RecoverPasswordScreen.route) {
            PasswordRecoveryScreen(passwordRecoveryViewModel = hiltViewModel()) { navController.navigate(route = LoginScreen.route) }
        }
        // endregion

        composable(route = MoviesHome.route) {
            MoviesHomeScreen(
                moviesHomeViewModel = hiltViewModel(),
                onClickSignOut = {
                    mainViewModel.signOut()
                    navController.navigateInclusiveTo(nextScreenRoute = LoginScreen.route, previousScreenRoute = MoviesHome.route)
                },
                onClickMovie = { movieId -> navController.navigateSingleTopTo(route = "${MovieDetail.route}/$movieId") },
            )
        }
        composable(route = MovieDetail.routeWithArgs, arguments = MovieDetail.arguments) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getInt(MovieDetail.MOVIE_ID_ARG)
            MovieDetailScreen(movieDetailViewModel = hiltViewModel(), movieId = movieId) { navController.popBackStack() }
        }
    }
}
