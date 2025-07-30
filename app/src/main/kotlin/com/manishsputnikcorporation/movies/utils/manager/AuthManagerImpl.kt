package com.manishsputnikcorporation.movies.utils.manager

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.manishsputnikcorporation.movies.R
import kotlinx.coroutines.tasks.await

interface AuthManager {

    fun getCurrentUser(): FirebaseUser?

    suspend fun signInAnonymously(): AuthRes<FirebaseUser>

    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthRes<FirebaseUser>

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthRes<FirebaseUser>

    suspend fun recoverPassword(email: String): AuthRes<Unit>

    fun signOut()
}

class AuthManagerImpl(val context: Context) : AuthManager {
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override suspend fun signInAnonymously(): AuthRes<FirebaseUser> =
        manageAuthResult(authResult = auth.signInAnonymously(), errorMessage = "sign_in_anonymously_error")

    override suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthRes<FirebaseUser> =
        manageAuthResult(
            authResult = auth.createUserWithEmailAndPassword(email, password),
            errorMessage = "create_user_with_email_and_password_error",
        )

    override suspend fun signInWithEmailAndPassword(email: String, password: String): AuthRes<FirebaseUser> =
        manageAuthResult(
            authResult = auth.signInWithEmailAndPassword(email, password),
            errorMessage = "sign_in_with_email_and_password_error",
        )

    override suspend fun recoverPassword(email: String): AuthRes<Unit> =
        try {
            auth.sendPasswordResetEmail(email).await()
            AuthRes.Success(Unit)
        } catch (e: Exception) {
            AuthRes.Error(e.message ?: context.getString(R.string.auth_password_recovery_error))
        }

    override fun signOut() {
        auth.signOut()
    }

    // region Helper methods
    private suspend fun manageAuthResult(authResult: Task<AuthResult>, errorMessage: String) =
        try {
            val firebaseUser = authResult.await().user
            AuthRes.Success(firebaseUser ?: throw Exception(errorMessage))
        } catch (e: Exception) {
            AuthRes.Error(e.message ?: errorMessage)
        }
    // endregion
}

sealed class AuthRes<out T> {
    data class Success<T>(val data: T) : AuthRes<T>()

    data class Error(val errorMessage: String) : AuthRes<Nothing>()
}
