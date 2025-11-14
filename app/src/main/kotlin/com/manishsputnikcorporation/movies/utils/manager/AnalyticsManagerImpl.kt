package com.manishsputnikcorporation.movies.utils.manager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.firebase.analytics.FirebaseAnalytics

interface AnalyticsManager {

    @SuppressLint("ComposableNaming") @Composable fun trackScreen(screenName: String)

    fun trackEvent(eventName: String, params: Bundle)

    fun trackButtonClick(buttonName: String)

    fun trackError(errorType: String)
}

class AnalyticsManagerImpl(context: Context) : AnalyticsManager {
    private val firebaseAnalytics: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }

    @Composable
    override fun trackScreen(screenName: String) {
        LaunchedEffect(key1 = screenName) {
            val params = Bundle().apply { putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName) }
            trackEvent(eventName = FirebaseAnalytics.Event.SCREEN_VIEW, params = params)
        }
    }

    override fun trackEvent(eventName: String, params: Bundle) {
        firebaseAnalytics.logEvent(eventName, params)
    }

    override fun trackButtonClick(buttonName: String) {
        val params = Bundle().apply { putString("button_name", buttonName) }
        trackEvent(eventName = "button_clicked", params = params)
    }

    override fun trackError(errorType: String) {
        val params = Bundle().apply { putString("error_type", errorType) }
        trackEvent(eventName = "error_triggered", params = params)
    }
}
