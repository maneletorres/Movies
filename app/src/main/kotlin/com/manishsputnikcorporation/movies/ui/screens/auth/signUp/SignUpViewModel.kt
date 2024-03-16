package com.manishsputnikcorporation.movies.ui.screens.auth.signUp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.manishsputnikcorporation.movies.ui.navigation.SignUpScreen
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.manager.AuthManager
import com.manishsputnikcorporation.movies.utils.manager.AuthRes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val analyticsManager: AnalyticsManager, private val authManager: AuthManager) :
    ViewModel() {

    // TODO: difference between sign in (Login/Iniciar sesión) with sign up (Registrarse)

    @SuppressLint("ComposableNaming") @Composable fun trackScreen() = analyticsManager.trackScreen(screenName = SignUpScreen.route)

    // TODO: el flujo debería de ser con 1 único click: si el usuario existe, adelante, sino, crea
    // la cuenta y adelante
    suspend fun createUser(email: String, password: String, onNavigation: () -> Unit) {
        analyticsManager.trackButtonClick("create_user")
        when (val result = authManager.createUserWithEmailAndPassword(email, password)) {
            is AuthRes.Success -> {
                analyticsManager.trackEvent(FirebaseAnalytics.Event.SIGN_UP, Bundle())
                signIn(email, password, onNavigation)
            }
            is AuthRes.Error -> {
                // TODO: show Snackbar
                analyticsManager.trackError(result.errorMessage)
            }
        }
    }

    private suspend fun signIn(email: String, password: String, onNavigation: () -> Unit) {
        when (val result = authManager.signInWithEmailAndPassword(email, password)) {
            is AuthRes.Success -> {
                analyticsManager.trackEvent(FirebaseAnalytics.Event.LOGIN, Bundle())
                onNavigation()
            }
            is AuthRes.Error -> {
                // TODO: show Snackbar
                analyticsManager.trackError(result.errorMessage)
            }
        }
    }
}
