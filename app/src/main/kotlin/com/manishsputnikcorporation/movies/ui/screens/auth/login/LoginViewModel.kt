package com.manishsputnikcorporation.movies.ui.screens.auth.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class LoginViewModel @Inject constructor(private val analyticsManager: AnalyticsManager, private val authManager: AuthManager) :
    ViewModel() {

    @SuppressLint("ComposableNaming") @Composable fun trackScreen() = analyticsManager.trackScreen(screenName = LoginScreen.route)

    // TODO: to find out an alternative for uncouple AuthManager from this VM.
    suspend fun incognitoSignIn(onNavigation: (String, String) -> Unit) {
        analyticsManager.trackButtonClick(buttonName = "incognito_sign_in")
        when (val result = authManager.signInAnonymously()) {
            is AuthRes.Success -> {
                analyticsManager.trackEvent(eventName = FirebaseAnalytics.Event.LOGIN, params = Bundle())
                onNavigation(MoviesHome.route, LoginScreen.route)
            }
            is AuthRes.Error -> {
                analyticsManager.trackError(errorType = result.errorMessage)
                // TODO: show Snackbar
            }
        }
    }

    fun navigateToSignUpScreen(onNavigation: (String, String) -> Unit) {
        analyticsManager.trackButtonClick(buttonName = "email_and_password_sign_in")
        onNavigation(SignUpScreen.route, LoginScreen.route)
    }

    fun navigateToPasswordRecoveryScreen(onNavigation: (String, String) -> Unit) {
        analyticsManager.trackButtonClick(buttonName = "forgotten_password")
        onNavigation(RecoverPasswordScreen.route, LoginScreen.route)
    }

    fun launchGoogleSignIn(context: Context, onNavigation: (String, String) -> Unit) {
//        val request: GetCredentialRequest =
//            GetCredentialRequest.Builder().addCredentialOption(credentialOption = authManager.getGoogleIdOption()).build()
//
//        viewModelScope.launch {
//            // TODO: mantener esto en estado para luego cerrarlo con credentialManager.clearCredentialState(clearRequest)
//            // TODO: esto va por inyección de dependencias
//            // TODO: actualizar la foto y el nombre con los datos obtenidos en Home
//            val credentialManager: CredentialManager = CredentialManager.create(context)
//            try {
//                val result: GetCredentialResponse = credentialManager.getCredential(request = request, context = context)
//                when (val result = authManager.signInWithGoogle(result = result)) {
//                    is AuthRes.Success -> {
//                        analyticsManager.trackEvent(eventName = FirebaseAnalytics.Event.LOGIN, params = Bundle())
//                        onNavigation(MoviesHome.route, LoginScreen.route)
//                    }
//                    is AuthRes.Error -> {
//                        Timber.e(javaClass.simpleName, "Exception: ${result.errorMessage}")
//                        analyticsManager.trackError(errorType = result.errorMessage)
//                        // TODO: show Snackbar
//                    }
//                }
//            } catch (e: GetCredentialException) {
//                Timber.e(javaClass.simpleName, "Exception: ${e.message}")
//                analyticsManager.trackError(errorType = e.message.orEmpty())
//                // TODO: show Snackbar
//            }
//        }
    }
}
