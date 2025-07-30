package com.manishsputnikcorporation.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.manager.AuthManager

@Composable
fun AppNavigation(mainViewModel: MainViewModel, navController: NavHostController, modifier: Modifier = Modifier) {
    val currentUser = mainViewModel.getCurrentUser()
    val startDestination = if (currentUser != null) MoviesHome.route else SplashScreen.route

    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable(route = SplashScreen.route) {
            SplashScreen(splashViewModel = hiltViewModel()) {
                navController.apply {
                    popBackStack()
                    navigateSingleTopTo(LoginScreen.route)
                }
            }
        }

        // region Auth screens:
        composable(route = LoginScreen.route) {
            LoginScreen(loginViewModel = hiltViewModel()) { nextScreen, previousScreen ->
                navController.navigateInclusiveTo(nextScreen, previousScreen)
            }
        }
        composable(route = SignUpScreen.route) {
            SignUpScreen(signUpViewModel = hiltViewModel()) { navController.navigateInclusiveTo(MoviesHome.route, LoginScreen.route) }
        }
        composable(route = RecoverPasswordScreen.route) {
            PasswordRecoveryScreen(passwordRecoveryViewModel = hiltViewModel()) { navController.navigate(LoginScreen.route) }
        }
        // endregion

        composable(route = MoviesHome.route) {
            MoviesHomeScreen(
                moviesHomeViewModel = hiltViewModel(),
                onClickSignOut = {
                    mainViewModel.signOut()
                    navController.navigateInclusiveTo(LoginScreen.route, MoviesHome.route)
                },
                onClickMovie = { movieId -> navController.navigateSingleTopTo("${MovieDetail.route}/$movieId") },
            )
        }
        composable(route = MovieDetail.routeWithArgs, arguments = MovieDetail.arguments) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getString(MovieDetail.movieIdArg)
            MovieDetailScreen(movieDetailViewModel = hiltViewModel(), movieId = movieId) {
                navController.popBackStack()
            }
        }
    }
}
