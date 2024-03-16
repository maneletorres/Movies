package com.manishsputnikcorporation.movies.ui.screens.auth.login

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.manishsputnikcorporation.movies.ui.navigation.LoginScreen
import com.manishsputnikcorporation.movies.ui.navigation.MoviesHome
import com.manishsputnikcorporation.movies.ui.navigation.RecoverPasswordScreen
import com.manishsputnikcorporation.movies.ui.navigation.SignUpScreen
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.manager.AuthManager
import com.manishsputnikcorporation.movies.utils.manager.AuthRes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val analyticsManager: AnalyticsManager, private val authManager: AuthManager) :
    ViewModel() {

    @SuppressLint("ComposableNaming") @Composable fun trackScreen() = analyticsManager.trackScreen(screenName = LoginScreen.route)

    // TODO: to find out an alternative for uncouple AuthManager from this VM.
    suspend fun incognitoSignIn(onNavigation: (String, String) -> Unit) {
        analyticsManager.trackButtonClick("incognito_sign_in")
        when (val result = authManager.signInAnonymously()) {
            is AuthRes.Success -> {
                analyticsManager.trackEvent(FirebaseAnalytics.Event.LOGIN, Bundle())
                onNavigation(MoviesHome.route, LoginScreen.route)
            }
            is AuthRes.Error -> {
                analyticsManager.trackError(result.errorMessage)
                // TODO: show Snackbar
            }
        }
    }

    fun navigateToSignUpScreen(onNavigation: (String, String) -> Unit) {
        analyticsManager.trackButtonClick("email_and_password_sign_in")
        onNavigation(SignUpScreen.route, LoginScreen.route)
    }

    fun navigateToPasswordRecoveryScreen(onNavigation: (String, String) -> Unit) {
        analyticsManager.trackButtonClick("forgotten_password")
        onNavigation(RecoverPasswordScreen.route, LoginScreen.route)
    }
}
