package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject
import org.json.JSONObject

class OnboardingEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun onboard1Completed() {
        tracker.track("onboard_1_completed")
    }

    fun onboard2Completed(
        isBirthdateEntered: Boolean,
        isActiveRegionEntered: Boolean
    ) {
        tracker.track(
            eventName = "onboard_2_completed",
            properties = JSONObject().apply {
                put("birthdate_entered", isBirthdateEntered)
                put("active_region_entered", isActiveRegionEntered)
            }
        )
    }

    fun onboard2Skipped() {
        tracker.track("onboard_2_skipped")
    }

    fun onboard3Completed(bioLength: Int) {
        tracker.track(
            eventName = "onboard_3_completed",
            properties = JSONObject().apply {
                put("bio_length", bioLength)
            }
        )
    }

    fun onboard3Skipped() {
        tracker.track("onboard_3_skipped")
    }
}
