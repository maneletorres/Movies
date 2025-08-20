package com.manishsputnikcorporation.movies.ui.navigation

import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.manishsputnikcorporation.movies.ui.MainViewModel
import com.manishsputnikcorporation.movies.ui.screens.auth.login.LoginScreen
import com.manishsputnikcorporation.movies.ui.screens.auth.passwordRecovery.PasswordRecoveryScreen
import com.manishsputnikcorporation.movies.ui.screens.auth.signUp.SignUpScreen
import com.manishsputnikcorporation.movies.ui.screens.detail.DetailScreen
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeScreen
import com.manishsputnikcorporation.movies.ui.screens.splash.SplashScreen
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.extensions.navigateInclusiveTo
import com.manishsputnikcorporation.movies.utils.extensions.navigateSingleTopTo
import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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
                homeViewModel = hiltViewModel(),
                onClickSignOut = {
                    mainViewModel.signOut()
                    navController.navigateInclusiveTo(nextScreenRoute = LoginScreen.route, previousScreenRoute = MoviesHome.route)
                },
                onClickMovie = { detailArgs ->
                    navController.navigateSingleTopTo(detailArgs = DetailArgs(id = detailArgs.id, type = detailArgs.type))
                },
            )
        }
        composable<DetailArgs>(typeMap = DetailArgs.typeMap) { navBackStackEntry ->
            DetailScreen(detailViewModel = hiltViewModel()) { navController.popBackStack() }
        }
    }
}

@Serializable
data class DetailArgs(val id: Int, val type: Type) {
    companion object {
        val typeMap: Map<KType, NavType<DetailArgs>> = mapOf(typeOf<DetailArgs>() to serializableType<DetailArgs>())

        fun from(savedStateHandle: SavedStateHandle) = savedStateHandle.toRoute<DetailArgs>(typeMap)
    }
}

inline fun <reified T : Any> serializableType(isNullableAllowed: Boolean = false, json: Json = Json) =
    object : NavType<T>(isNullableAllowed = isNullableAllowed) {
        override fun get(bundle: Bundle, key: String) = bundle.getString(key)?.let<String, T>(json::decodeFromString)

        override fun parseValue(value: String): T = json.decodeFromString(value)

        override fun serializeAsValue(value: T): String = Uri.encode(json.encodeToString(value))

        override fun put(bundle: Bundle, key: String, value: T) = bundle.putString(key, json.encodeToString(value))
    }
