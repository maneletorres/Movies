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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class SignUpViewModel @Inject constructor(private val analyticsManager: AnalyticsManager, private val authManager: AuthManager) :
    ViewModel() {

    private val _event: Channel<Event> = Channel()
    val event: Flow<Event> = _event.receiveAsFlow()

    @SuppressLint("ComposableNaming") @Composable fun trackScreen() = analyticsManager.trackScreen(screenName = SignUpScreen.route)

    suspend fun createUser(email: String, password: String, onNavigation: () -> Unit) {
        analyticsManager.trackButtonClick(buttonName = "create_user")
        when (val result = authManager.createUserWithEmailAndPassword(email, password)) {
            is AuthRes.Success -> {
                analyticsManager.trackEvent(eventName = FirebaseAnalytics.Event.SIGN_UP, params = Bundle())
                signIn(email, password, onNavigation)
            }
            is AuthRes.Error -> {
                val errorMessage = result.errorMessage
                analyticsManager.trackError(errorType = result.errorMessage)
                _event.send(element = Event.UserCreationFailed(errorMessage = errorMessage))
            }
        }
    }

    suspend fun signIn(email: String, password: String, onNavigation: () -> Unit) {
        when (val result = authManager.signInWithEmailAndPassword(email, password)) {
            is AuthRes.Success -> {
                analyticsManager.trackEvent(eventName = FirebaseAnalytics.Event.LOGIN, params = Bundle())
                onNavigation()
            }
            is AuthRes.Error -> {
                val errorMessage = result.errorMessage
                analyticsManager.trackError(errorType = errorMessage)
                _event.send(element = Event.SignInFailed(errorMessage = errorMessage))
            }
        }
    }

    // region States & Events
    sealed interface Event {
        data class UserCreationFailed(val errorMessage: String) : Event

        data class SignInFailed(val errorMessage: String) : Event
    }
    // endregion
}
