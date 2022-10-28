package com.maneletorres.simpletemplate.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Contract for information needed on every Movies navigation destination
 */
interface MoviesDestination {
    val route: String
}

/**
 * Movies app navigation destinations
 */
object MoviesHome : MoviesDestination {
    override val route: String = "home"
}

object MovieDetail : MoviesDestination {
    override val route: String = "detail"
    const val movieIdArg = "movie_id"
    val routeWithArgs = "$route/{$movieIdArg}"
    val arguments = listOf(navArgument(movieIdArg) { type = NavType.StringType })
}

