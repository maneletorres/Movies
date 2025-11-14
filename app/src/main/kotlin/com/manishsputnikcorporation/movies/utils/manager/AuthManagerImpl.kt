package com.manishsputnikcorporation.movies.utils.manager

import android.content.Context
import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import com.google.android.gms.tasks.Task
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.manishsputnikcorporation.movies.R
import kotlinx.coroutines.tasks.await
import timber.log.Timber

interface AuthManager {

    fun getCurrentUser(): FirebaseUser?

    fun getGoogleIdOption(): GetGoogleIdOption

    suspend fun signInAnonymously(): AuthRes<FirebaseUser>

    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthRes<FirebaseUser>

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthRes<FirebaseUser>

    suspend fun signInWithGoogle(result: GetCredentialResponse): AuthRes<FirebaseUser?>

    suspend fun recoverPassword(email: String): AuthRes<Unit>

    fun signOut()
}

class AuthManagerImpl(context: Context) : AuthManager {
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    private val webClientId = context.getString(R.string.default_web_client_id)

    private val googleIdOption: GetGoogleIdOption =
        GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .setAutoSelectEnabled(true)
            // nonce string to use when generating a Google ID token
            .setNonce("") // TODO: to apply nonce
            .build()

    // TODO: ¿como se usa esto?
    val signInWithGoogleOption: GetSignInWithGoogleOption =
        GetSignInWithGoogleOption.Builder(serverClientId = webClientId).setNonce("").build() // TODO: to apply nonce

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override fun getGoogleIdOption(): GetGoogleIdOption = googleIdOption

    override suspend fun signInAnonymously(): AuthRes<FirebaseUser> =
        manageAuthResult(authResult = auth.signInAnonymously(), errorMessage = "sign_in_anonymously_error")

    override suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthRes<FirebaseUser> {
        val errorMessage = "create_user_with_email_and_password_error"
        return try {
            manageAuthResult(authResult = auth.createUserWithEmailAndPassword(email, password), errorMessage = errorMessage)
        } catch (e: Exception) {
            AuthRes.Error(errorMessage = e.message ?: errorMessage)
        }
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): AuthRes<FirebaseUser> {
        val errorMessage = "sign_in_with_email_and_password_error"
        return try {
            manageAuthResult(authResult = auth.signInWithEmailAndPassword(email, password), errorMessage = errorMessage)
        } catch (e: Exception) {
            AuthRes.Error(errorMessage = e.message ?: errorMessage)
        }
    }

    override suspend fun signInWithGoogle(result: GetCredentialResponse): AuthRes<FirebaseUser?> =
        // TODO: podríamos prescidir de este doble mecanismo de seguridad perfectamente...
        try {
            handleSignIn(result = result)
        } catch (e: Exception) {
            AuthRes.Error(errorMessage = e.message ?: "sign_in_with_google_error")
        }

    override suspend fun recoverPassword(email: String): AuthRes<Unit> =
        try {
            auth.sendPasswordResetEmail(email).await()
            AuthRes.Success(data = Unit)
        } catch (e: Exception) {
            AuthRes.Error(errorMessage = e.message ?: "password_recovery_error")
        }

    override fun signOut() = auth.signOut()

    suspend fun handleSignIn(result: GetCredentialResponse): AuthRes<FirebaseUser?> {
        val credential = result.credential
        val responseJson: String

        return when (credential) {
            is PublicKeyCredential -> {
                // Share responseJson such as a GetCredentialResponse to your server to validate and
                // authenticate
                responseJson = credential.authenticationResponseJson
                AuthRes.Success(data = null)
            }
            is PasswordCredential -> {
                // Send ID and password to your server to validate and authenticate.
                val username = credential.id
                val password = credential.password
                AuthRes.Success(data = null)
            }
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential: GoogleIdTokenCredential = GoogleIdTokenCredential.createFrom(data = credential.data)
                        firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        val errorMessage = "Received an invalid google id token response"
                        Log.e("PEPE", errorMessage, e)
                        AuthRes.Error(errorMessage = e.message ?: errorMessage)
                    }
                } else {
                    val errorMessage = "Unexpected type of credential"
                    Log.e("PEPE", errorMessage)
                    AuthRes.Error(errorMessage = errorMessage)
                }
            }
            else -> {
                val errorMessage = "Unexpected type of credential"
                Log.e("PEPE", errorMessage)
                AuthRes.Error(errorMessage = errorMessage)
            }
        }
    }

    // TODO: to test this method
    private suspend fun firebaseAuthWithGoogle(idToken: String): AuthRes<FirebaseUser?> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return try {
            val firebaseUser = auth.signInWithCredential(credential).await().user
            AuthRes.Success(data = firebaseUser ?: throw Exception())
        } catch (e: Exception) {
            // TODO: crear un mensaje apropiado
            Timber.e(javaClass.simpleName, "firebaseAuthWithGoogle:failure", e.message)
            AuthRes.Error(errorMessage = e.message ?: "")
        }
    }

    // region Helper methods
    private suspend fun manageAuthResult(authResult: Task<AuthResult>, errorMessage: String) =
        try {
            val firebaseUser = authResult.await().user
            AuthRes.Success(data = firebaseUser ?: throw Exception(errorMessage))
        } catch (e: Exception) {
            AuthRes.Error(errorMessage = e.message ?: errorMessage)
        }
    // endregion
}

sealed class AuthRes<out T> {
    data class Success<T>(val data: T) : AuthRes<T>()

    data class Error(val errorMessage: String) : AuthRes<Nothing>()
}
