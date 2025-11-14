package com.manishsputnikcorporation.movies.ui

import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.ClearCredentialStateRequest.Companion.TYPE_CLEAR_RESTORE_CREDENTIAL
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.manager.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel
@Inject
constructor(private val authManager: AuthManager, val analyticsManager: AnalyticsManager, val credentialManager: CredentialManager) :
    ViewModel() {

    fun getCurrentUser() = authManager.getCurrentUser()

    fun signOut() {
        viewModelScope.launch {
            val clearRequest = ClearCredentialStateRequest(requestType = TYPE_CLEAR_RESTORE_CREDENTIAL)
            credentialManager.clearCredentialState(request = clearRequest)
            authManager.signOut()
        }
    }
}
