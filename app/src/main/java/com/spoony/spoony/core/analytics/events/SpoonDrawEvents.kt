package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject

class SpoonDrawEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun spoonReceived(spoonCount: Int) {
        tracker.track(
            eventName = "spoon_received",
            properties = """
                {
                    "spoon_count": $spoonCount
                }
            """.trimIndent()
        )
    }
}
