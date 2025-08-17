package com.manishsputnikcorporation.movies.utils.extensions

import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTopTo(route: String) = navigate(route) { launchSingleTop = true }

fun NavHostController.navigateInclusiveTo(nextScreenRoute: String, previousScreenRoute: String) =
    navigate(nextScreenRoute) { popUpTo(previousScreenRoute) { inclusive = true } }
