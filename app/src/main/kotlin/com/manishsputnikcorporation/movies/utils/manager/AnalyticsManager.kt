package com.manishsputnikcorporation.movies.utils.manager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsManager(context: Context) {
    private val firebaseAnalytics: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }

    private fun trackEvent(eventName: String, params: Bundle) {
        firebaseAnalytics.logEvent(eventName, params)
    }

    @SuppressLint("ComposableNaming")
    fun trackButtonClick(buttonName: String) {
        val params = Bundle().apply { putString("button_name", buttonName) }
        trackEvent(eventName = "button_clicked", params = params)
    }

    @SuppressLint("ComposableNaming")
    @Composable
    fun trackScreen(screenName: String) {
        DisposableEffect(Unit) {
            onDispose {
                val params =
                    Bundle().apply {
                        putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
                        putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
                    }
                trackEvent(eventName = FirebaseAnalytics.Event.SCREEN_VIEW, params = params)
            }
        }
    }

    fun trackError(errorType: String) {
        val params = Bundle().apply { putString("error_type", errorType) }
        trackEvent(eventName = "error_triggered", params = params)
    }
}
