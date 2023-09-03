package com.manishsputnikcorporation.movies.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

/** Contract for information needed on every Movies navigation destination */
// TODO: to rename to AppScreens?
interface MoviesDestination {
  val route: String
}

/** Movies app navigation destinations */
object SplashScreen : MoviesDestination {
  override val route: String = "splash_screen"
}

object LoginScreen : MoviesDestination {
  override val route: String = "login_screen"
}

object SignInScreen : MoviesDestination {
  override val route: String = "sign_in_screen"
}

object RecoverPasswordScreen : MoviesDestination {
  override val route: String = "recover_password_screen"
}

object MoviesHome : MoviesDestination {
  override val route: String = "home_screen"
}

object MovieDetail : MoviesDestination {
  override val route: String = "detail_screen"
  const val movieIdArg = "movie_id"
  val routeWithArgs = "$route/{$movieIdArg}"
  val arguments = listOf(navArgument(movieIdArg) { type = NavType.StringType })
}
