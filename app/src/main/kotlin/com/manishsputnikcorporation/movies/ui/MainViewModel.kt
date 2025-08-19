package com.manishsputnikcorporation.movies.ui

import androidx.lifecycle.ViewModel
import com.manishsputnikcorporation.movies.utils.manager.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authManager: AuthManager) : ViewModel() {

    fun getCurrentUser() = authManager.getCurrentUser()

    fun signOut() = authManager.signOut()
}
