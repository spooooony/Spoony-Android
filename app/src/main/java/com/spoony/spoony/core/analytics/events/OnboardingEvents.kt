package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject

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
            properties = """
                {
                    "birthdate_entered": $isBirthdateEntered,
                    "active_region_entered": $isActiveRegionEntered
                }
            """.trimIndent()
        )
    }

    fun onboard2Skipped() {
        tracker.track("onboard_2_skipped")
    }

    fun onboard3Completed(bioLength: Int) {
        tracker.track(
            eventName = "onboard_3_completed",
            properties = """
                {
                    "bio_length": $bioLength
                }
            """.trimIndent()
        )
    }

    fun onboard3Skipped() {
        tracker.track("onboard_3_skipped")
    }
}
