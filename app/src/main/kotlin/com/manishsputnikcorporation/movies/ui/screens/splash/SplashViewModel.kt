package com.manishsputnikcorporation.movies.ui.screens.splash

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.manishsputnikcorporation.movies.ui.navigation.SplashScreen
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val analyticsManager: AnalyticsManager) : ViewModel() {

    @SuppressLint("ComposableNaming") @Composable fun trackScreen() = analyticsManager.trackScreen(screenName = SplashScreen.route)
}
