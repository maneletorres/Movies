package com.manishsputnikcorporation.movies.ui.screens.auth.login

import android.content.Context
import android.os.Bundle
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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val analyticsManager: AnalyticsManager,
    private val authManager: AuthManager,
    private val credentialManager: CredentialManager,
) : ViewModel() {

    private val _loginEvent: Channel<LoginEvent> = Channel()
    val loginEvent: Flow<LoginEvent> = _loginEvent.receiveAsFlow()

    suspend fun incognitoSignIn(onNavigation: (String, String) -> Unit) {
        analyticsManager.trackButtonClick(buttonName = "incognito_sign_in")
        when (val result = authManager.signInAnonymously()) {
            is AuthRes.Success -> {
                analyticsManager.trackEvent(eventName = FirebaseAnalytics.Event.LOGIN, params = Bundle())
                onNavigation(MoviesHome.route, LoginScreen.route)
            }
            is AuthRes.Error -> {
                val errorMessage = result.errorMessage
                analyticsManager.trackError(errorType = errorMessage)
                _loginEvent.send(element = LoginEvent.SignInFailed(errorMessage = errorMessage))
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
        val request: GetCredentialRequest =
            GetCredentialRequest.Builder().addCredentialOption(credentialOption = authManager.getGoogleIdOption()).build()

        viewModelScope.launch {
            // TODO: mantener esto en estado para luego cerrarlo con credentialManager.clearCredentialState(clearRequest)
            try {
                val result: GetCredentialResponse = credentialManager.getCredential(request = request, context = context)
                when (val result = authManager.signInWithGoogle(result = result)) {
                    is AuthRes.Success -> {
                        analyticsManager.trackEvent(eventName = FirebaseAnalytics.Event.LOGIN, params = Bundle())
                        onNavigation(MoviesHome.route, LoginScreen.route)
                    }
                    is AuthRes.Error -> {
                        val errorMessage = result.errorMessage
                        Timber.e(javaClass.simpleName, "Exception: $errorMessage")
                        analyticsManager.trackError(errorType = errorMessage)
                        _loginEvent.send(element = LoginEvent.SignInFailed(errorMessage = errorMessage))
                    }
                }
            } catch (e: GetCredentialException) {
                val errorMessage = e.message.orEmpty()
                Timber.e(javaClass.simpleName, "Exception: $errorMessage")
                analyticsManager.trackError(errorType = errorMessage)
                _loginEvent.send(element = LoginEvent.SignInFailed(errorMessage = errorMessage))
            }
        }
    }

    // region States & Events
    sealed interface LoginEvent {
        data class SignInFailed(val errorMessage: String) : LoginEvent
    }
    // endregion
}
