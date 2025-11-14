package com.manishsputnikcorporation.movies.ui.screens.auth.passwordRecovery

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.manager.AuthManager
import com.manishsputnikcorporation.movies.utils.manager.AuthRes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PasswordRecoveryViewModel @Inject constructor(private val analyticsManager: AnalyticsManager, private val authManager: AuthManager) :
    ViewModel() {

    private val _event: Channel<Event> = Channel()
    val event: Flow<Event> = _event.receiveAsFlow()

    fun onRecoverPasswordClicked(email: String) {
        // TODO: to move to a UseCase
        analyticsManager.trackButtonClick(buttonName = "recover_password")

        viewModelScope.launch {
            when (val res = authManager.recoverPassword(email)) {
                is AuthRes.Success -> {
                    val bundle = Bundle().apply { putString("email", email) }
                    analyticsManager.trackEvent(eventName = "successful_password_recovery", params = bundle)
                    _event.send(element = Event.PasswordRecoverySuccessful)
                }
                is AuthRes.Error -> {
                    analyticsManager.trackError(errorType = "failed_password_recovery: $email - ${res.errorMessage}")
                    _event.send(element = Event.PasswordRecoveryFailed)
                }
            }
        }
    }

    sealed interface Event {

        data object PasswordRecoverySuccessful : Event

        data object PasswordRecoveryFailed : Event
    }
}
