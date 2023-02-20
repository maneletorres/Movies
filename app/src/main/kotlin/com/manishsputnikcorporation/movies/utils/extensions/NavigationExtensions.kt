package com.manishsputnikcorporation.movies.utils.extensions

import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTopTo(route: String) =
    navigate(route) { launchSingleTop = true }
