//package com.manishsputnikcorporation.movies.utils.manager
//
//import com.google.firebase.Firebase
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.auth.auth
//import kotlinx.coroutines.tasks.await
//
//class AuthManager {
//    private val auth: FirebaseAuth by lazy { Firebase.auth }
//    suspend fun signInAnonymously(): AuthRes<FirebaseUser> {
//        return try {
//            val result = auth.signInAnonymously().await()
//            AuthRes.Success(result.user ?: throw Exception("sign_in_error"))
//        } catch (e: Exception) {
//            AuthRes.Error(e.message ?: "sign_in_error")
//        }
//    }
//
//    fun signOut() {
//        auth.signOut()
//    }
//
//    fun getCurrentUser(): FirebaseUser? = auth.currentUser
//}
//
//sealed class AuthRes<out T> {
//    data class Success<T>(val data: T) : AuthRes<T>()
//
//    data class Error(val errorMessage: String) : AuthRes<Nothing>()
//}
