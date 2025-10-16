package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject
import org.json.JSONObject

class AnalyticsEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun appOpen() {
        tracker.track("app_open")
    }

    fun signupCompleted(signupMethod: String) {
        tracker.track(
            eventName = "signup_completed",
            properties = JSONObject().apply {
                put("signup_method", signupMethod)
            }
        )
    }

    fun loginSuccess() {
        tracker.track("login_success")
    }
}
