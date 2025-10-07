package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject

class MixPanelUserProperties @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun setUserProfile(userId: String, properties: Map<String, Any>) {
        tracker.setUserProfile(userId = userId, properties = properties)
    }

    fun resetUserProfile() {
        tracker.resetUserProfile()
    }
}
