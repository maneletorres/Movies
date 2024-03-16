package com.manishsputnikcorporation.movies.ui.screens.auth.passwordRecovery

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishsputnikcorporation.movies.ui.navigation.RecoverPasswordScreen
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.manager.AuthManager
import com.manishsputnikcorporation.movies.utils.manager.AuthRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordRecoveryViewModel @Inject constructor(private val analyticsManager: AnalyticsManager, private val authManager: AuthManager) :
    ViewModel() {

    private val _event: Channel<Event> = Channel()
    val event: Flow<Event> = _event.receiveAsFlow()

    @SuppressLint("ComposableNaming") @Composable fun trackScreen() = analyticsManager.trackScreen(screenName = RecoverPasswordScreen.route)

    fun onRecoverPasswordClicked(email: String) {
        // TODO: to move to a UseCase
        analyticsManager.trackButtonClick("recover_password")

        viewModelScope.launch {
            when (val res = authManager.recoverPassword(email)) {
                is AuthRes.Success -> {
                    val bundle = Bundle().apply { putString("email", email) }
                    analyticsManager.trackEvent("successful_password_recovery", bundle)
                    _event.send(Event.SuccessfulPasswordRecovery)
                }
                is AuthRes.Error -> {
                    analyticsManager.trackError("failed_password_recovery: $email - ${res.errorMessage}")
                    _event.send(Event.FailedPasswordRecovery)
                }
            }
        }
    }

    sealed interface Event {

        data object SuccessfulPasswordRecovery : Event

        data object FailedPasswordRecovery : Event
    }
}
