package com.manishsputnikcorporation.movies.utils.extensions

import androidx.navigation.NavHostController
import com.manishsputnikcorporation.movies.ui.navigation.DetailArgs

fun NavHostController.navigateSingleTopTo(route: String) = navigate(route) { launchSingleTop = true }

fun NavHostController.navigateSingleTopTo(detailArgs: DetailArgs) = navigate(detailArgs) { launchSingleTop = true }

fun NavHostController.navigateInclusiveTo(nextScreenRoute: String, previousScreenRoute: String) =
    navigate(nextScreenRoute) { popUpTo(previousScreenRoute) { inclusive = true } }
