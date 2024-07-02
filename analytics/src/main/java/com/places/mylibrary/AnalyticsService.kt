package com.places.mylibrary

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsService @Inject constructor(
    private val analytics: FirebaseAnalytics
) {

    fun trackOpenApp() {
        analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN) {
            param("APP_OPEN", "APP_OPEN")
        }
    }
}