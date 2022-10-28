package com.maneletorres.simpletemplate.utils

import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTopTo(route: String) =
    navigate(route) { launchSingleTop = true }