package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject

class AnalyticsEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun appOpen() {
        tracker.track("app_open")
    }

    fun signupCompleted(signupMethod: String) {
        tracker.track(
            eventName = "signup_completed",
            properties = """
                {
                    "signup_method": "$signupMethod"
                }
            """.trimIndent()
        )
    }

    fun loginSuccess() {
        tracker.track("login_success")
    }
}
